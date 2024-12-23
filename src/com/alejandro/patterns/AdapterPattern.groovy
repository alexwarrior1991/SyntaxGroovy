package com.alejandro.patterns

//The Adapter Pattern (sometimes called the wrapper pattern) allows objects satisfying one interface to be used
//where another type of interface is expected. There are two typical flavours of the pattern:
//        the delegation flavour and the inheritance flavour.

//Suppose we have the following classes:
class SquarePeg {
    def width
}

class RoundPeg {
    def radius
}

class RoundHole {
    def radius

    def pegFits(peg) {
        peg.radius <= radius
    }

    String toString() { "RoundHole with radius $radius" }
}

//We can ask the RoundHole class if a RoundPeg fits in it, but if we ask the same question for a SquarePeg, then it will fail because the SquarePeg class doesn’t have a radius property (i.e. doesn’t satisfy the required interface).
//
//To get around this problem, we can create an adapter to make it appear to have the correct interface. It would look like this:

/*
class SquarePegAdapter {
    def peg

    def getRadius() {
        Math.sqrt(((peg.width / 2)**2) * 2)
    }

    String toString() {
        "SquarePegAdapter with peg width $peg.width (and notional radius $radius)"
    }
}

//We can use the adapter like this:
def hole = new RoundHole(radius: 4.0)
(4..7).each { w ->
    def peg = new SquarePegAdapter(peg: new SquarePeg(width: w))
    if (hole.pegFits(peg)) {
        println "peg $peg fits in hole $hole"
    } else {
        println "peg $peg does not fit in hole $hole"
    }
}
*/

//Inheritance Example
//Let’s consider the same example again using inheritance. First, here are the original classes (unchanged):

class SquarePegAdapter extends SquarePeg {
    def getRadius() {
        Math.sqrt(((width / 2)**2) * 2)
    }

    String toString() {
        "SquarePegAdapter with width $width (and notional radius $radius)"
    }
}

//Using the adapter:
def hole = new RoundHole(radius: 4.0)
(4..7).each { w ->
    def peg = new SquarePegAdapter(width: w)
    if (hole.pegFits(peg)) {
        println "peg $peg fits in hole $hole"
    } else {
        println "peg $peg does not fit in hole $hole"
    }
}

//Adapting using Closures
//As a variation of the previous examples, we could instead define the following interface:

interface RoundThing {
    def getRadius()
}

//We can then define an adapter as a closure as follows:
def adapter = {
    p -> [getRadius: { Math.sqrt(((p.width / 2)**2) * 2) }] as RoundThing
}

def peg = new SquarePeg(width: 4)
if (hole.pegFits(adapter(peg))) {
    println "peg $peg fits in hole $hole"
} else {
    println "peg $peg does not fit in hole $hole"
}

//vamos a crear un ejemplo más completo en el que adaptamos una clase `Rectangle`
//para que implemente una interfaz `Shape` que tenga más de un método.

interface Shape {
    def getArea()

    def getPerimeter()
}

class Rectangle {
    double width
    double height

    Rectangle(double width, double height) {
        this.width = width
        this.height = height
    }

    double calculateArea() {
        return width * height
    }

    double calculatePerimeter() {
        return 2 * (width + height)
    }
}

// Definiendo un adaptador como un cierre
def adapterS = { rect ->
    [
            getArea     : { rect.calculateArea() },
            getPerimeter: { rect.calculatePerimeter() }
    ] as Shape
}

Rectangle rectangle = new Rectangle(4, 6)
Shape shape = adapterS(rectangle)

println("Area: " + shape.getArea())          // Imprime: Area: 24.0
println("Perimeter: " + shape.getPerimeter()) // Imprime: Perimeter: 20.0