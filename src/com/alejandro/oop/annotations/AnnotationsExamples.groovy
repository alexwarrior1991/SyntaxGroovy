package com.alejandro.oop.annotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

//Annotation definition

/*
An annotation is a kind of special interface dedicated at annotating elements of the code. An annotation is a type which
superinterface is the java.lang.annotation.Annotation interface. Annotations are declared in a very similar way to interfaces, using the @interface keyword:


An annotation may define members in the form of methods without bodies and an optional default value. The possible member types are limited to:

primitive types

java.lang.String

java.lang.Class

an java.lang.Enum

another java.lang.annotation.Annotation

or any array of the above




@interface SomeAnnotation {
    String value()
}
@interface SomeAnnotation {
    String value() default 'something'
}
@interface SomeAnnotation {
    int step()
}
@interface SomeAnnotation {
    Class appliesTo()
}
@interface SomeAnnotation {}
@interface SomeAnnotations {
    SomeAnnotation[] value()
}
enum DayOfWeek { mon, tue, wed, thu, fri, sat, sun }
@interface Scheduled {
    DayOfWeek dayOfWeek()

an annotation defining a value member of type String
an annotation defining a value member of type String with a default value of something
an annotation defining a step member of type the primitive type int
an annotation defining a appliesTo member of type Class
an annotation defining a value member which type is an array of another annotation type
an annotation defining a dayOfWeek member which type is the enumeration type DayOfWeek



 */

//Annotation placement

/*
An annotation can be applied on various elements of the code:

@SomeAnnotation applies to the someMethod method
@SomeAnnotation applies to the SomeClass class
@SomeAnnotation applies to the var variable

In order to limit the scope where an annotation can be applied, it is necessary to declare it on the annotation definition,
using the java.lang.annotation.Target annotation. For example, here is how you would declare that an annotation can be applied to a class or a method:

import java.lang.annotation.ElementType
import java.lang.annotation.Target

@Target([ElementType.METHOD, ElementType.TYPE])
@interface SomeAnnotation {}

the @Target annotation is meant to annotate an annotation with a scope.
@SomeAnnotation will therefore only be allowed on TYPE or METHOD
*/

//Annotation member values

@interface Page {
    String value()

    int statusCode() default 200
}

@Page(value = '/home')
void home() {
    // ...
}

@Page('/users')
void userList() {
    // ...
}

@Page(value = 'error', statusCode = 404)
void notFound() {
    // ...
}

//we can omit the statusCode because it has a default value, but value needs to be set
//since value is the only mandatory member without a default, we can omit value=
//if both value and statusCode need to be set, it is required to use value= for the default value member

/*
//Retention policy

//The visibility of an annotation depends on its retention policy. The retention policy of an annotation
//is set using the java.lang.annotation.Retention annotation:

@Retention(RetentionPolicy.SOURCE)
@interface SomeAnnotation {}

//the @Retention annotation annotates the @SomeAnnotation annotation
//so @SomeAnnotation will have a SOURCE retention

 */

//Ejemplo
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import java.lang.reflect.Modifier


// Paso 1: Definir una anotación personalizada
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.METHOD])
@interface Auditable {
    String value() default "DEFAULT" // Un atributo opcional para la anotación
}

// Paso 2: Crear una clase que utilice la anotación
class ServicioA {
    @Auditable("Creación")
    void crearRecurso(String nombre) {
        println "Recurso '${nombre}' creado."
    }

    @Auditable("Eliminación")
    void eliminarRecurso(String nombre) {
        println "Recurso '${nombre}' eliminado."
    }

    void metodoNormal() {
        println "Este método no está anotado."
    }
}

// Paso 3: Procesar la anotación dinámicamente usando meta-programación
def procesarAuditoria(ServicioA servicio) {
    // Obtener los métodos de la clase que tienen la anotación @Auditable
    servicio.metaClass.methods.each { method ->
        if (method.getAnnotation(Auditable)) {
            def originalMethod = servicio.&"${method.name}"
            servicio.metaClass."${method.name}" = { Object... args ->
                // Interceptar la ejecución del método
                def anotacion = method.getAnnotation(Auditable)
                println "【AUDITORÍA】: Iniciando método '${method.name}' - Acción: ${anotacion.value()}"
                def resultado = originalMethod(*args) // Llamar al método original
                println "【AUDITORÍA】: Finalizando método '${method.name}' - Acción: ${anotacion.value()}"
                return resultado
            }
        }
    }
}

// Paso 4: Ejecutar y probar
def servicio = new ServicioA()

// Procesar dinámicamente las anotaciones de la clase
procesarAuditoria(servicio)

// Llamar métodos para ver el procesamiento
servicio.crearRecurso("Archivo1")
servicio.eliminarRecurso("Archivo2")
servicio.metodoNormal() // Este método no está anotado, funciona normalmente


//Closure annotation parameters

/*
An interesting feature of annotations in Groovy is that you can use a closure as an annotation value.
Therefore annotations may be used with a wide variety of expressions and still have IDE support.
For example, imagine a framework where you want to execute some methods based on environmental constraints like the JDK version
or the OS. One could write the following code:
 */

//For the @OnlyIf annotation to accept a Closure as an argument, you only have to declare the value as a Class:

@Retention(RetentionPolicy.RUNTIME)
@interface OnlyIf {
    Class value()
}

class Tasks {
    Set result = []

    void alwaysExecuted() {
        result << 1
    }

    @OnlyIf({ jdk >= 6 })
    void supportedOnlyInJDK6() {
        result << 'JDK 6'
    }

    @OnlyIf({ jdk >= 7 && windows })
    void requiresJDK7AndWindows() {
        result << 'JDK 7 Windows'
    }
}

//To complete the example, let’s write a sample runner that would use that information:

class Runner {
    static <T> T run(Class<T> taskClass) {
        def tasks = taskClass.newInstance()
        def params = [jdk: 6, windows: false]
        tasks.class.declaredMethods.each { m ->
            if (Modifier.isPublic(m.modifiers) && m.parameterTypes.length == 0) {
                def onlyIf = m.getAnnotation(OnlyIf)
                if (onlyIf) {
                    Closure cl = onlyIf.value().newInstance(tasks, tasks)
                    cl.delegate = params
                    if (cl()) {
                        m.invoke(tasks)
                    }
                } else {
                    m.invoke(tasks)
                }
            }
        }
        tasks
    }
}

//create a new instance of the class passed as an argument (the task class)
//emulate an environment which is JDK 6 and not Windows
//iterate on all declared methods of the task class
//if the method is public and takes no-argument
//try to find the @OnlyIf annotation
//if it is found get the value and create a new Closure out of it
//set the delegate of the closure to our environment variable
//call the closure, which is the annotation closure. It will return a boolean
//if it is true, call the method
//if the method is not annotated with @OnlyIf, execute the method anyway
//after that, return the task object

def tasks = Runner.run(Tasks)
assert tasks.result == [1, 'JDK 6'] as Set

