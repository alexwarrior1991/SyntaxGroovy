package com.alejandro.oop.members

import groovy.transform.MapConstructor
import groovy.test.GroovyAssert

//Constructors

/*
Groovy supports two invocation styles:

    positional parameters are used in a similar to how you would use Java constructors

    named parameters allow you to specify parameter names when invoking the constructor.
*/

//Positional parameters

class PersonConstructor {
    String name
    Integer age

    PersonConstructor(name, age) {
        this.name = name
        this.age = age
    }
}

def person1 = new PersonConstructor('Marie', 1)
def person2 = ['Marie', 2] as PersonConstructor
PersonConstructor person3 = ['Marie', 3]

//Constructor declaration
//Constructor invocation, classic Java way
//Constructor usage, using coercion with as keyword
//Constructor usage, using coercion in assignment

//Named parameters

//Having a constructor where the first (and perhaps only) argument is a Map argument is also supported
//- such a constructor may also be added using the groovy.transform.MapConstructor annotation.

@MapConstructor
class PersonWOConstructor {
    String name
    Integer age
}

def person4 = new PersonWOConstructor()
def person5 = new PersonWOConstructor(name: 'Marie')
def person6 = new PersonWOConstructor(age: 1)
def person7 = new PersonWOConstructor(name: 'Marie', age: 2)

//Methods

//Method definition

//Methods in Groovy always return some value. If no return statement is provided, the value evaluated in the
//last line executed will be returned. For instance, note that none of the following methods uses the return keyword.

def someMethod() { 'method called' }

String anotherMethod() { 'another method called' }

def thirdMethod(param1) { "$param1 passed" }

static String fourthMethod(String param1) { "$param1 passed" }

//Method with no return type declared and no parameter
//Method with explicit return type and no parameter
//Method with a parameter with no type defined
//Static method with a String parameter

//Named parameters

def foo(Map args) {
    "${args.name}: ${args.age}"
}

println foo(name: 'Marie', age: 1)

def foo(Map args, Integer number) { "${args.name}: ${args.age}, and the number is ${number}" }

println foo(name: 'Marie', age: 1, 23)
println(foo(23, name: 'Marie', age: 1))

//Method call with additional number argument of Integer type
//Method call with changed order of arguments

//If we don’t have the Map as the first argument, then a Map must be supplied for that argument instead of named parameters.
//        Failure to do so will lead to groovy.lang.MissingMethodException:


//Default arguments
def foo(String par1, Integer par2 = 1) {
    [name: par1, age: par2]
}

assert foo('Marie').age == 1

//Varargs

def foo(Object... args) { args.length }

assert foo() == 0
assert foo(1) == 1
assert foo(1, 2) == 2

//If a method with varargs is called with null as the vararg parameter, then the argument will be null and not an array of length one with null as the only element.

def foo2(Object... args) { args }

assert foo2(null) == null

//If a varargs method is called with an array as an argument, then the argument will be that array instead of an array of length one containing the given array as the only element.

Integer[] ints = [1, 2]
assert foo2(ints) == [1, 2]

//method overloading

def foo3(Object... args) { 1 }

def foo3(Object x) { 2 }

assert foo3() == 1
assert foo3(1) == 2
assert foo3(1, 2) == 1


//Method selection algorithm
def method(Object o1, Object o2) { 'o/o' }

def method(Integer i, String s) { 'i/s' }

def method(String s, Integer i) { 's/i' }

assert method('foo', 42) == 's/i'

List<List<Object>> pairs = [['foo', 1], [2, 'bar'], [3, 4]]
assert pairs.collect { a, b -> method(a, b) } == ['s/i', 'i/s', 'o/o']

//Exception declaration

//Groovy automatically allows you to treat checked exceptions like unchecked exceptions. This means that you don’t need to
//declare any checked exceptions that a method may throw as shown in the following example which can throw a FileNotFoundException if the file isn’t found:

def badRead() throws FileNotFoundException {
    new File('doesNotExist.txt').text
}

GroovyAssert.shouldFail(FileNotFoundException) {
    badRead()
}

//Fields

//A field is a member of a class, interface or trait which stores data. A field defined in a Groovy source file has:
//
//a mandatory access modifier (public, protected, or private)
//
//one or more optional modifiers (static, final, synchronized)
//
//an optional type
//
//a mandatory name

class Data {
    private int id
    protected String description
    public static final boolean DEBUG = false
}

//a private field named id, of type int
//a protected field named description, of type String
//a public static final field named DEBUG of type boolean

//It is possible to omit the type declaration of a field. This is however considered a bad practice and in general it is a good idea to use strong typing for fields:
class BadPractice {
    private mapping
}

class GoodPractice {
    private Map<String, String> mapping
}

//Properties

/*
A property is an externally visible feature of a class. Rather than just using a public field to represent such features
(which provides a more limited abstraction and would restrict refactoring possibilities), the typical approach in Java
is to follow the conventions outlined in the JavaBeans Specification, i.e. represent the property using a combination
of a private backing field and getters/setters. Groovy follows these same conventions but provides a simpler way to define the property.

You can define a property with:

an absent access modifier (no public, protected or private)

one or more optional modifiers (static, final, synchronized)

an optional type

a mandatory name

Groovy will then generate the getters/setters appropriately. For example:
 */

/*
class Person {
    String name
    int age
}*/

//creates a backing private String name field, a getName and a setName method
//creates a backing private int age field, a getAge and a setAge method

//If a property is declared final, no setter is generated:

/*
class Person {
    final String name
    final int age
    Person(String name, int age) {
        this.name = name
        this.age = age
    }
}*/

//defines a read-only property of type String
//defines a read-only property of type int
//assigns the name parameter to the name field
//assigns the age parameter to the age field

//Properties are accessed by name and will call the getter or setter transparently, unless the code is in the class which defines the property:
class Person {
    String name

    void name(String name) {
        this.name = "Wonder $name"
    }

    String title() {
        this.name
    }
}

def p = new Person()
p.name = 'Diana'
assert p.name == 'Diana'
p.name('Woman')
assert p.title() == 'Wonder Woman'

//this.name will directly access the field because the property is accessed from within the class that defines it
//similarly a read access is done directly on the name field
//write access to the property is done outside of the Person class so it will implicitly call setName
//read access to the property is done outside of the Person class so it will implicitly call getName
//this will call the name method on Person which performs a direct access to the field
//this will call the title method on Person which performs a direct read access to the field

//It is worth noting that this behavior of accessing the backing field directly is done in order to prevent
//a stack overflow when using the property access syntax within a class that defines the property.

//By convention, Groovy will recognize properties even if there is no backing field provided there are getters or setters
//that follow the Java Beans specification. For example:

class PseudoProperties {
    // a pseudo property "name"
    void setName(String name) {}

    String getName() {}

    // a pseudo read-only property "age"
    int getAge() { 42 }

    // a pseudo write-only property "groovy"
    void setGroovy(boolean groovy) {}
}

p = new PseudoProperties()
p.name = 'Foo'
assert p.age == 42
p.groovy = true

//writing p.name is allowed because there is a pseudo-property name
//reading p.age is allowed because there is a pseudo-readonly property age
//writing p.groovy is allowed because there is a pseudo-write-only property groovy

//Annotations on a property

//Annotations, including those associated with AST transforms, are copied on to the backing field for the property.
//        This allows AST transforms which are applicable to fields to be applied to properties, e.g.:

class Animal {
    int lowerCount = 0
    @Lazy
    String name = method()

    def method = {
        lower().toUpperCase()
    }

    String lower() { lowerCount++; 'sloth' }
}

def a = new Animal()
assert a.lowerCount == 0
assert a.name == 'SLOTH'
assert a.lowerCount == 1

//Split property definition with an explicit backing field
class Cuenta {
    // Campo de respaldo explícito
    private BigDecimal _balance = 0

    // Definición de la propiedad con getters y setters personalizados
    BigDecimal getBalance() {
        return _balance
    }

    void setBalance(BigDecimal balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("El balance no puede ser negativo")
        }
        _balance = balance
    }

    // Metodo para aumentar el balance
    void depositar(BigDecimal cantidad) {
        _balance += cantidad
    }
}

def cuenta = new Cuenta()
cuenta.depositar(100)
println "Balance después del depósito: ${cuenta.balance}"

try {
    cuenta.balance = -50  // Intento de establecer un balance negativo
} catch (IllegalArgumentException e) {
    println "Error al establecer el balance: ${e.message}"
}

import groovy.transform.PackageScope


//Another example
class Persona {
    // Campo de respaldo explícito
    private String _name

    // Getter y setter personalizados
    String getName() {
        return _name
    }

    void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío")
        }
        _name = name
    }

    // Campo de respaldo explícito con visibilidad de paquete
    @PackageScope
    String _hiddenInfo = "Información confidencial"

    // Método para modificar _hiddenInfo, accesible dentro del paquete
    @PackageScope
    void setHiddenInfo(String info) {
        _hiddenInfo = info
    }
}

// Demostración del uso en el mismo paquete
def persona = new Persona()
persona.name = "Juan"
println "Nombre: ${persona.name}"

// El siguiente acceso será posible si está en el mismo paquete
println "Acceso a información confidencial en el mismo paquete: ${persona._hiddenInfo}"

// Ajustar la información confidencial
persona.setHiddenInfo("Nueva información")
println "Información confidencial actualizada: ${persona._hiddenInfo}"








class Producto {
    // Campo de respaldo explícito para nombre del producto
    private String _nombre

    // Getter para nombre (público)
    String getNombre() {
        return _nombre
    }

    // Setter para nombre con validación simple
    void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío")
        }
        _nombre = nombre
    }

    // Propiedad con visibilidad de paquete para SKU (número de identificación)
    @PackageScope
    String sku

    // Método con visibilidad de paquete para modificar SKU
    @PackageScope
    void setSku(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("El SKU no puede estar vacío")
        }
        this.sku = sku
    }
}

// En el mismo paquete, podemos demostrar cómo se accede a las propiedades:
def producto = new Producto()
producto.nombre = "Laptop"

// Esto es accesible porque `nombre` es un método público
println "Nombre del producto: ${producto.nombre}"

// Acceso a SKU y método `setSku`, que son @PackageScope y, por lo tanto, accesibles dentro del mismo paquete:
producto.setSku("123-ABC")
println "SKU del producto: ${producto.sku}"
