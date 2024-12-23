package com.alejandro.basics

import groovy.transform.Canonical
import groovy.transform.CompileStatic

//Aritmethic operators

assert 1 + 2 == 3
assert 4 - 3 == 1
assert 3 * 5 == 15
assert 3 / 2 == 1.5
assert 10 % 3 == 1
assert 2**3 == 8

//Unary operators

def a = 2
def b = a++ * 3

assert a == 3 && b == 6

def c = 3
def d = c-- * 2

assert c == 2 && d == 6

def e = 1
def f = ++e + 3

assert e == 2 && f == 5

def g = 4
def h = --g + 1

assert g == 3 && h == 4

//The postfix increment will increment a after the expression has been evaluated and assigned into b
//The postfix decrement will decrement c after the expression has been evaluated and assigned into d
//The prefix increment will increment e before the expression is evaluated and assigned into f
//The prefix decrement will decrement g before the expression is evaluated and assigned into h

//Assignment arithmetic operators
a = 4
a += 3

assert a == 7

b = 5
b -= 3

assert b == 2

c = 5
c *= 3

assert c == 15

d = 10
d /= 2

d == 5

e = 10
e %= 3

assert e == 1

f = 3
f **= 2

assert f == 9

//Relational operators

assert 1 + 2 == 3
assert 3 != 4

assert -2 < 3
assert 2 <= 2
assert 3 <= 4

assert 5 > 1
assert 5 >= -2

//Both === and !== are supported which are the same as calling the is() method, and negating a call to the is() method respectively.

@EqualsAndHashCode
class Creature {
    String type;
}

def cat = new Creature(type: 'cat');
def copyCat = cat;
def lion = new Creature(type: 'cat');

assert cat.equals(lion); // Java logical equality
assert cat == lion;     // Groovy shorthand operator

assert cat.is(copyCat)  // Groovy identity
assert cat === copyCat  // operator shorthand
assert cat !== lion     // negated operator shorthand

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.util.stream.Collectors

@EqualsAndHashCode
class Person {
    String nombre
    Integer edad
}

def persona1 = new Person(nombre: "Juan", edad: 30)
def persona2 = persona1
def persona3 = new Person(nombre: "Juan", edad: 30)

println "Comparación de contenido:"
println "persona1 == persona2: ${persona1 == persona2}"    // Verdadero
println "persona1 == persona3: ${persona1 == persona3}"    // Verdadero

println "\nComparación de referencia:"
println "persona1 === persona2: ${persona1 === persona2}" // Verdadero
println "persona1 === persona3: ${persona1 === persona3}" // Falso

println "\nComparación de referencia con is():"
println "persona1.is(persona2): ${persona1.is(persona2)}" // Verdadero
println "persona1.is(persona3): ${persona1.is(persona3)}" // Falso

//Logical operators

//short circuiting
//The logical || operator supports short-circuiting: if the left operand is true, it knows that the result will be true in any case,
//so it won’t evaluate the right operand. The right operand will be evaluated only if the left operand is false.
//
//Likewise for the logical && operator: if the left operand is false, it knows that the result will be false in any case,
//so it won’t evaluate the right operand. The right operand will be evaluated only if the left operand is true.

boolean checkIfCalled() {
    called = true
}

called = false
true || checkIfCalled()
assert !called

called = false
false || checkIfCalled()
assert called

called = false
false && checkIfCalled()
assert !called

called = false
true && checkIfCalled()
assert called

//We create a function that sets the called flag to true whenever it’s called
//In the first case, after resetting the called flag, we confirm that if the left operand to || is true, the function is not called, as || short-circuits the evaluation of the right operand
//In the second case, the left operand is false and so the function is called, as indicated by the fact our flag is now true
//Likewise for &&, we confirm that the function is not called with a false left operand
//But the function is called with a true left operand

//Bitshift y Bitwise operators

//1. **AND Bit a Bit (`&`)**: Realiza una operación AND bit a bit.
//2. **OR Bit a Bit (`|`)**: Realiza una operación OR bit a bit.
//3. **XOR Bit a Bit (`^`)**: Realiza una operación XOR bit a bit.
//4. **Complemento Bit a Bit (`~`)**: Realiza una operación NOT bit a bit (complemento a uno).


//#### Ejemplo 1: Operador AND Bit a Bit (`&`)
a = 0b1100 // 12 en sistema decimal
b = 0b1010 // 10 en sistema decimal
def result = a & b // Operador AND bit a bit

println Integer.toBinaryString(result) // Imprime "1000", que es 8 en sistema decimal

//#### Ejemplo 2: Operador OR Bit a Bit (`|`)
a = 0b1100 // 12 en sistema decimal
b = 0b1010 // 10 en sistema decimal
result = a | b // Operador OR bit a bit

println Integer.toBinaryString(result) // Imprime "1110", que es 14 en sistema decimal

//#### Ejemplo 3: Operador XOR Bit a Bit (`^`)
a = 0b1100 // 12 en sistema decimal
b = 0b1010 // 10 en sistema decimal
result = a ^ b // Operador XOR bit a bit

println Integer.toBinaryString(result) // Imprime "0110", que es 6 en sistema decimal

//#### Ejemplo 4: Operador NOT Bit a Bit (`~`)
a = 0b1100 // 12 en sistema decimal
result = ~a // Operador NOT bit a bit

println Integer.toBinaryString(result) // Imprime "11111111111111111111111111110011" si se considera un entero de 32 bits (complemento a dos de -13 en decimal)

//#### Ejemplo 5: Desplazamiento a la Izquierda (`<<`)
a = 0b0011 // 3 en sistema decimal
result = a << 2 // Desplaza los bits 2 posiciones a la izquierda

println Integer.toBinaryString(result) // Imprime "1100", que es 12 en sistema decimal

//#### Ejemplo 6: Desplazamiento a la Derecha (`>>`)
a = 0b1100 // 12 en sistema decimal
result = a >> 2 // Desplaza los bits 2 posiciones a la derecha

println Integer.toBinaryString(result) // Imprime "0011", que es 3 en sistema decimal

//#### Ejemplo 7: Desplazamiento a la Derecha Sin Signo (`>>>`)
a = -12 // Número negativo en sistema decimal
result = a >>> 2 // Desplaza los bits 2 posiciones a la derecha sin signo

println Integer.toBinaryString(result) // Imprime "00111111111111111111111111111100", que es 1073741821 en sistema decimal para un entero de 32 bits

//Conditional operators
class Direccion {
    String ciudad
}

class Persona {
    String nombre
    Direccion direccion

    String saludar() {
        return "Hola, soy $nombre y vivo en ${direccion.ciudad.toString()}"
    }
}

def persona = new Persona(nombre: "Juan", direccion: new Direccion(ciudad: "Madrid"))
def personaSinDireccion = new Persona(nombre: "Pedro")
def personaNull = null;

println persona?.saludar() // Imprime "Hola, soy Juan"
println personaNull?.saludar() // Imprime "null" sin lanzar excepción
println persona?.direccion?.ciudad; // Imprime "Madrid"
println personaSinDireccion?.direccion?.ciudad // Imprime "null" sin lanzar excepción
println personaNull?.direccion?.ciudad // Imprime "null" sin lanzar excepción


def lista = [new Persona(nombre: "Juan"), null, new Persona(nombre: "Pedro"), new Persona(nombre: "Juan", direccion: new Direccion(ciudad: "Madrid"))]
def nombres = lista.collect { it?.nombre }
def ciudades = lista.collect { it?.direccion?.ciudad }

println nombres
println ciudades

@ToString(includePackage = false)
class Element {
    String name;
    Integer atomicNumber;
}

def he = new Element(name: 'Helium');
he.with {
    name = name ?: 'Hydrogen'   // existing Elvis operator
    atomicNumber ?= 2
}

assert he.toString() == 'Element(Helium, 2)'

//Direct field access operators

//Normally in Groovy, when you write code like this:
class User {
    public final String name;

    User(String name) {
        this.name = name;
    }

    String getName() {
        return "Name: $name"
    }
}

def user = new User('Bob')
assert user.name == 'Name: Bob'

//The user.name call triggers a call to the property of the same name, that is to say, here, to the getter for name.
//If you want to retrieve the field instead of calling the getter, you can use the direct field access operator:

assert user.@name == 'Bob';

//Method pointer operator

//The method pointer operator (.&) can be used to store a reference to a method in a variable, in order to call it later:
def str = 'example of method reference'
def fun = str.&toUpperCase;
def upper = fun();
assert upper == str.toUpperCase();

//the str variable contains a String
//we store a reference to the toUpperCase method on the str instance inside a variable named fun
//fun can be called like a regular method
//we can check that the result is the same as if we had called it directly on str

//There are multiple advantages in using method pointers. First of all, the type of such a method pointer is a groovy.lang.
//Closure, so it can be used in any place a closure would be used.
//In particular, it is suitable to convert an existing method for the needs of the strategy pattern:

def transform(List elements, Closure action) {
    def result = [];
    elements.each {
        it -> result << action.call(it)
    }
    return result
}

String describe(Person p) {
    "$p.nombre is $p.edad"
}

def action = this.&describe

def list = [
        new Person(nombre: 'Bob', edad: 42),
        new Person(nombre: 'Julia', edad: 35)
]

assert transform(list, action) == ['Bob is 42', 'Julia is 35'];

//the transform method takes each element of the list and calls the action closure on them, returning a new list
//we define a function that takes a Person and returns a String
//we create a method pointer on that function
//we create the list of elements we want to collect the descriptors
//the method pointer can be used where a Closure was expected

//Method pointers are bound by the receiver and a method name. Arguments are resolved at runtime,
//meaning that if you have multiple methods with the same name,
// the syntax is not different, only resolution of the appropriate method to be called will be done at runtime:

def doSomething(String str) { str.toUpperCase() }

def doSomething(Integer x) { 2 * x }

def reference = this.&doSomething
assert reference('foo') == 'FOO'
assert reference(123) == 246

//Define an overloaded doSomething method accepting a String as an argument
//define an overloaded doSomething method accepting an Integer as an argument
//create a single method pointer on doSomething, without specifying argument types
//using the method pointer with a String calls the String version of doSomething
//using the method pointer with an Integer calls the Integer version of doSomething

def instanceMethod = String.&toUpperCase
assert instanceMethod('foo') == 'FOO'

//Method reference operator

@CompileStatic
void methodRefs() {
    assert 6G == [1G, 2G, 3G].stream().reduce(0G, BigInteger::add)

    assert [4G, 5G, 6G] == [1G, 2G, 3G].stream().map(3G::add).collect(Collectors.toList())

    assert [1G, 2G, 3G] == [1L, 2L, 3L].stream().map(BigInteger::valueOf).collect(Collectors.toList())

    assert [1G, 2G, 3G] == [1L, 2L, 3L].stream().map(3G::valueOf).collect(Collectors.toList())
}

methodRefs()

//class instance method reference: add(BigInteger val) is an instance method in BigInteger
//object instance method reference: add(BigInteger val) is an instance method for object 3G
//class static method reference: valueOf(long val) is a static method for class BigInteger
//object static method reference: valueOf(long val) is a static method for object 3G (some consider this bad style in normal circumstances)

//Some examples highlighting various supported constructor reference cases are shown in the following script:

@CompileStatic
void constructorRefs() {
    assert [1, 2, 3] == ['1', '2', '3'].stream().map(Integer::valueOf).collect(Collectors.toList());

    def result = [1, 2, 3].stream().toArray(Integer[]::new)
    assert result instanceof Integer[]
    assert result.toString() == '[1, 2, 3]'
}

constructorRefs();

/*
//Pattern operator
//The pattern operator (~) provides a simple way to create a java.util.regex.Pattern instance:
def p = ~/foo/
assert p instanceof Pattern

p = ~'foo'
p = ~"foo"
p = ~$/dollar/slashy $ string/$
p = ~"${pattern}"

//using single quote strings
//using double quotes strings
//the dollar-slashy string lets you use slashes and the dollar sign without having to escape them
//you can also use a GString!

//Alternatively to building a pattern, you can use the find operator =~ to directly create a java.util.regex.Matcher instance:
def text = "some text to match"
def m = text =~ /match/
assert m instanceof Matcher
if (!m) {
    throw new RuntimeException("Oops, text not found!")
}
//=~ creates a matcher against the text variable, using the pattern on the right hand side
//the return type of =~ is a Matcher
//equivalent to calling if (!m.find(0))

def pattern = "\\b\\w{4,}\\b" // Este patrón busca palabras de 4 o más letras
def pt = ~pattern

// Texto largo donde buscar el patrón
def txt = """
Groovy is an object-oriented programming language for the Java platform.
It is a dynamic language with features similar to those of Python, Ruby, and Smalltalk.
It can be used as both a scripting language and a programming language.
"""

// Crear un matcher para buscar el patrón en el texto
def matcher = pt.matcher(txt)

// Imprimir todas las coincidencias encontradas
println("Ocurrencias de palabras con 4 o más letras:")
while (matcher.find()) {
    println matcher.group()
}

*/

// Spread operator

//The Spread-dot Operator (*.), often abbreviated to just Spread Operator, is used to invoke an action on all items of an
//aggregate object. It is equivalent to calling the action on each item and collecting the result into a list:

class Car {
    String make;
    String model;
}

def cars = [
        new Car(make: 'Peugeot', model: '508'),
        new Car(make: 'Renault', model: 'Clio')
]
def makes = cars*.make;

//The expression cars*.make is equivalent to cars.collect{ it.make }
def makesCollect = cars.collect { it -> it.make }
assert makes == ['Peugeot', 'Renault']
assert makesCollect == ['Peugeot', 'Renault']

//The spread operator is null-safe, meaning that if an element of the collection is null,
//it will return null instead of throwing a NullPointerException:
cars = [
        new Car(make: 'Peugeot', model: '508'),
        null,
        new Car(make: 'Renault', model: 'Clio')]
assert cars*.make == ['Peugeot', null, 'Renault']
assert null*.make == null

//build a list for which one of the elements is null
//using the spread operator will not throw a NullPointerException
//the receiver might also be null, in which case the return value is null

//The spread operator can be used on any class which implements the Iterable interface:
class Component {
    Integer id;
    String name;
}

class CompositeObject implements Iterable<Component> {

    def components = [
            new Component(id: 1, name: 'Foo'),
            new Component(id: 2, name: 'Bar')]

    @Override
    Iterator<Component> iterator() {
        return components.iterator();
    }
}

def composite = new CompositeObject()
assert composite*.id == [1, 2]
assert composite*.name == ['Foo', 'Bar']

//Use multiple invocations of the spread-dot operator (here cars*.models*.name)
//when working with aggregates of data structures which themselves contain aggregates:
class Make {
    String name
    List<Model> models
}

@Canonical
class Model {
    String name
}

cars = [
        new Make(name: 'Peugeot',
                models: [new Model('408'), new Model('508')]),
        new Make(name: 'Renault',
                models: [new Model('Clio'), new Model('Captur')])
]

makes = cars*.name;
assert makes == ['Peugeot', 'Renault']

def models = cars*.models*.name;
assert models == [['408', '508'], ['Clio', 'Captur']]
assert models.sum() == ['408', '508', 'Clio', 'Captur'] // flatten one level
assert models.flatten() == ['408', '508', 'Clio', 'Captur'] // flatten all levels (one in this case)
println models.flatten()

//Consider using the collectNested DGM method instead of the spread-dot operator for collections of collections:
cars = [
        [
                new Car(make: 'Peugeot', model: '408'),
                new Car(make: 'Peugeot', model: '508')
        ], [
                new Car(make: 'Renault', model: 'Clio'),
                new Car(make: 'Renault', model: 'Captur')
        ]
]

models = cars.collectNested { it -> it.model }
assert models == [['408', '508'], ['Clio', 'Captur']]


//Spreading method arguments
static int function(int x, int y, int z) {
    x * y + z
}

def args = [4, 5, 6]

assert function(*args) == 26

//It is even possible to mix normal arguments with spread ones:

args = [4]
assert function(*args, 5, 6) == 26

//Spread list elements
//When used inside a list literal, the spread operator acts as if the spread element contents were inlined into the list:

def items = [4, 5]
list = [1, 2, 3, *items, 6]
assert list == [1, 2, 3, 4, 5, 6]

//items is a list
//we want to insert the contents of the items list directly into list without having to call addAll
//the contents of items has been inlined into list

//Spread map elements

//The spread map operator works in a similar manner as the spread list operator, but for maps.
//It allows you to inline the contents of a map into another map literal, like in the following example:

def m1 = [c: 3, d: 4]
def map = [a: 1, b: 2, *: m1]
assert map == [a: 1, b: 2, c: 3, d: 4]

//m1 is the map that we want to inline
//we use the *:m1 notation to spread the contents of m1 into map
//map contains all the elements of m1

//The position of the spread map operator is relevant, like illustrated in the following example:
m1 = [c: 3, d: 4]
map = [a: 1, b: 2, *: m1, d: 8]
assert map == [a: 1, b: 2, c: 3, d: 8]

//Spaceship operator

//The spaceship operator (<=>) delegates to the compareTo method:
assert (1 <=> 1) == 0
assert (1 <=> 2) == -1
assert (2 <=> 1) == 1
assert ('a' <=> 'z') == -1

Comparator<Integer> comparador = { low, high -> high <=> low } as Comparator

TreeMap<Integer, String> treeMap = new TreeMap<>(comparador)
treeMap.put(3, "Tres")
treeMap.put(1, "Uno")
treeMap.put(2, "Dos")

println treeMap // Output: {3=Tres, 2=Dos, 1=Uno}


class Student {
    String name
    int age

    Student(String name, int age) {
        this.name = name
        this.age = age
    }

    @Override
    String toString() {
        return "$name ($age)"
    }
}

def studentComparator = { Student s1, Student s2 ->
    s1.age <=> s2.age // Comparación basada en la edad
}

treeMap = new TreeMap(studentComparator)

treeMap[new Student('Alice', 30)] = 'Engineer'
treeMap[new Student('Bob', 25)] = 'Designer'
treeMap[new Student('Charlie', 35)] = 'Manager'

treeMap.each { key, value ->
    println "$key: $value"
}

//Range operator (0..5))

//Groovy supports the concept of ranges and provides a notation (..) to create ranges of objects:
def range = 0..5

assert (0..5).collect() == [0, 1, 2, 3, 4, 5]
assert (0..<5).collect() == [0, 1, 2, 3, 4]
assert (0<..5).collect() == [1, 2, 3, 4, 5]
assert (0<..<5).collect() == [1, 2, 3, 4]
assert (0..5) instanceof List
assert (0..5).size() == 6

//a simple range of integers, stored into a local variable
//an IntRange, with inclusive bounds
//an IntRange, with exclusive upper bound
//an IntRange, with exclusive lower bound
//an IntRange, with exclusive lower and upper bounds
//a groovy.lang.Range implements the List interface
//meaning that you can call the size method on it

class Transaction {
    String id
    BigDecimal amount
    String type

    Transaction(String id, BigDecimal amount, String type) {
        this.id = id
        this.amount = amount
        this.type = type
    }

    @Override
    String toString() {
        return "Transaction(id: $id, amount: $amount, type: $type)"
    }
}

def transactions = [
        new Transaction('1', new BigDecimal('100.50'), 'credit'),
        new Transaction('2', new BigDecimal('200.00'), 'debit'),
        new Transaction('3', new BigDecimal('50.75'), 'credit'),
        new Transaction('4', new BigDecimal('300.25'), 'debit'),
        new Transaction('5', new BigDecimal('120.00'), 'credit')
]

def transactionStats = transactions.collect(new HashSet()) {
    transaction -> [type: transaction.type, amount: transaction.amount]
}

// Convert to a Map to summarize amounts by type
def summarizedStats = transactionStats.groupBy { it.type }
        .collectEntries { type, trans -> [(type): trans*.amount.sum()]
        }

println "Transactions: $transactions"
println "Transaction Stats: $transactionStats"
println "Transaction map: ${transactionStats.groupBy { it.type }}"
println "Summarized Stats: $summarizedStats"


//collect entreis
assert [a: 1, b: 2].collectEntries { key, value -> [value, key] } == [1: 'a', 2: 'b']
assert [a: 1, b: 2].collectEntries { key, value ->
    [(value * 10): key.toUpperCase()]
} == [10: 'A', 20: 'B']

//Subscript operator[]

//The subscript operator is a shorthand notation for getAt or putAt, depending on whether you find it on the left hand side
//or the right hand side of an assignment:

class Usuario {
    Long id
    String name

    def getAt(int i) {
        switch (i) {
            case 0: return id
            case 1: return name
        }
        throw new IllegalArgumentException("No such element $i")
    }

    void putAt(int i, def value) {
        switch (i) {
            case 0: id = value; return
            case 1: name = value; return
        }
        throw new IllegalArgumentException("No such element $i")
    }
}

user = new Usuario(id: 1, name: 'Alex')
assert user[0] == 1
assert user[1] == 'Alex'
user[1] = 'Bob'
assert user.name == 'Bob'

//the User class defines a custom getAt implementation
//the User class defines a custom putAt implementation
//create a sample user
//using the subscript operator with index 0 allows retrieving the user id
//using the subscript operator with index 1 allows retrieving the user name
//we can use the subscript operator to write to a property thanks to the delegation to putAt
//and check that it’s really the property name which was changed

//Safe index operator
//Groovy 3.0.0 introduces safe indexing operator, i.e. ?[], which is similar to ?.. For example:


String[] array = ['a', 'b']
assert 'b' == array?[1]      // get using normal array index
array?[1] = 'c'              // set using normal array index
assert 'c' == array?[1]

array = null
assert null == array?[1]     // return null for all index values
array?[1] = 'c'              // quietly ignore attempt to set value
assert null == array?[1]

def personInfo = [name: 'Daniel.Sun', location: 'Shanghai']
assert 'Daniel.Sun' == personInfo?['name']      // get using normal map index
personInfo?['name'] = 'sunlan'                  // set using normal map index
assert 'sunlan' == personInfo?['name']

personInfo = null
assert null == personInfo?['name']              // return null for all map values
personInfo?['name'] = 'sunlan'                  // quietly ignore attempt to set value
assert null == personInfo?['name']

//Membership operator
//The membership operator (in) is equivalent to calling the isCase method. In the context of a List,
//it is equivalent to calling contains, like in the following example:

list = ['Grace', 'Rob', 'Emmy']
assert ('Emmy' in list)
assert ('Alex' !in list)