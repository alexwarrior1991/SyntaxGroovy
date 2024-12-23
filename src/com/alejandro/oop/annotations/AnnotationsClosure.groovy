package com.alejandro.oop.annotations

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

// Paso 1: Definir la anotación que recibirá una clase que actúe como Closure
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.METHOD]) // Aplicable a métodos
@interface ClosureAnnotation {
    Class closure()
}

// Paso 2: Crear una clase con métodos anotados con la closure
class Servicio {
    @ClosureAnnotation(closure = { args -> args[0] > 0 ? "Válido" : "Inválido" })
    String validarNumero(int numero) {
        return "Número: ${numero}"
    }

    @ClosureAnnotation(closure = { args -> args[0].toUpperCase() })
    String transformarTexto(String texto) {
        return "Texto Transformado"
    }
}

// Paso 3: Procesar dinámicamente las anotaciones y sobrescribir los métodos
def procesarMetodosConAnotaciones(servicio) {
    // Iterar sobre los métodos declarados con reflection
    servicio.metaClass.methods.each { method ->
        def closureAnnotation = method.getAnnotation(ClosureAnnotation) // Obtener la anotación
        if (closureAnnotation) {
            def closureClass = closureAnnotation.closure() // Obtener la clase Closure desde la anotación
            def originalMethod = servicio.&"${method.name}" // Referencia al método original

            // Sobrescribir el método dinámicamente
            servicio.metaClass."${method.name}" = { Object... args ->
                println "【ANOTACIÓN】: Procesando método '${method.name}' con Closure"

                // Instanciar y ejecutar la closure
                def closure = closureClass.newInstance()
                def closureResult = closure(args) // Pasar los argumentos a la closure

                // Mostrar el resultado de la closure
                println "Resultado de la closure: ${closureResult}"

                // Ejecutar el método original y retornar su resultado
                return originalMethod(*args)
            }
        }
    }
}

// Paso 4: Crear una instancia del servicio, procesar sus anotaciones y probar
def servicio = new Servicio()
procesarMetodosConAnotaciones(servicio) // Procesar las anotaciones para sobrescribir los métodos

// Pruebas para verificar el procesamiento de las closures y anotaciones
println servicio.validarNumero(5)   // Resultado esperado: Válido y mensaje de anotación
println servicio.validarNumero(-1)  // Resultado esperado: Inválido y mensaje de anotación

println servicio.transformarTexto("groovy") // Resultado esperado: GROOVY y mensaje de anotación

