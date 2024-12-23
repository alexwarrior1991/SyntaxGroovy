package com.alejandro.oop.classes

/*

Groovy classes are very similar to Java classes, and are compatible with Java ones at JVM level. They may have methods, fields and properties (think JavaBeans properties but with less boilerplate). Classes and class members can have the same modifiers (public, protected, private, static, etc.) as in Java with some minor differences at the source level which are explained shortly.

The key differences between Groovy classes and their Java counterparts are:

    Classes or methods with no visibility modifier are automatically public (a special annotation can be used to achieve package private visibility).

    Fields with no visibility modifier are turned into properties automatically, which results in less verbose code,
    since explicit getter and setter methods aren’t needed. More on this aspect will be covered in the fields and properties section.

    Classes do not need to have the same base name as their source file definitions but it is highly
    recommended in most scenarios (see also the next point about scripts).

    One source file may contain one or more classes (but if a file contains any code not in a class, it is considered a script).
    Scripts are just classes with some special conventions and will have the same name as their source file
    (so don’t include a class definition within a script having the same name as the script source file).

The following code presents an example class.

*/

class Person {

    String name
    Integer age

    def increaseAge(Integer years) {
        this.age += years
    }
}

//class beginning, with the name Person
//string field and property named name
//method definition

def p = new Person()

//Inner class

class Outer {
    private String privateStr

    def callInnerMethod() {
        new Inner().methodA()
    }

    class Inner {
        def methodA() {
            println "${privateStr}"
        }
    }
}

//the inner class is instantiated and its method gets called
//inner class definition, inside its enclosing class
//even being private, a field of the enclosing class is accessed by the inner class


//It is common for an inner class to be an implementation of some interface whose method(s) are needed by the outer class.
//The code below illustrates this typical usage pattern, here being used with threads.

class Outer2 {
    private String privateStr = 'some string'

    def startThread() {
        new Thread(new Inner2()).start()
    }

    class Inner2 implements Runnable {

        @Override
        void run() {
            print("${privateStr}")
        }
    }
}

//Groovy 3+ also supports Java syntax for non-static inner class instantiation, for example:

class Computer {
    class Cpu {
        int coreNumber

        Cpu(int coreNumber) {
            this.coreNumber = coreNumber
        }
    }
}


//Anonymous inner class

//The earlier example of an inner class (Inner2) can be simplified with an anonymous inner class.
//The same functionality can be achieved with the following code:

class Outer3 {

    private String privateStr = 'some string'

    def startThread() {
        new Thread({ -> println("${privateStr}") } as Runnable).start()
    }
}

//Comparing with the last example of previous section, the new Inner2() was replaced by new Runnable() along with all its implementation
//the method start is invoked normally
//Thus, there was no need to define a new class to be used just once.

//Abstract class

abstract class Abstract {
    String name

    abstract def abstractMethod()

    def concreteMethod() {
        println 'concrete'
    }
}

//abstract classes must be declared with abstract keyword
//abstract methods must also be declared with abstract keyword


//Interfaces

interface Greeter {
    void greet(String name)
}

class SystemGreeter implements Greeter {
    void greet(String name) {
        println "Hello $name"
    }
}

def greeter = new SystemGreeter()
assert greeter instanceof Greeter

interface ExtendedGreeter extends Greeter {
    void sayBye(String name)
}


//It is worth noting that for a class to be an instance of an interface, it has to be explicit. For example, the following
//class defines the greet method as it is declared in the Greeter interface, but does not declare Greeter in its interfaces:

class DefaultGreeter {
    void greet(String name) { println "Hello" }
}

greeter = new DefaultGreeter()
assert !(greeter instanceof Greeter)

greeter = new DefaultGreeter()
coerced = greeter as Greeter
assert coerced instanceof Greeter 