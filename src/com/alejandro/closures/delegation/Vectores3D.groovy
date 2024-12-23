package com.alejandro.closures.delegation

// Clase que representa un vector en 3D
class Vector3D {
    double x
    double y
    double z

    void print() {
        println "Vector(x: $x, y: $y, z: $z)"
    }

    // Método para calcular la magnitud del vector
    double magnitude() {
        return Math.sqrt(x * x + y * y + z * z)
    }
}

// Función que aplica operaciones a un vector usando un closure y delegate
def applyOperations(Vector3D vector, Closure operations) {
    operations.delegate = vector
    operations.resolveStrategy = Closure.DELEGATE_FIRST
    operations()
}

// Crear una instancia de Vector3D
def vector = new Vector3D(x: 1.0, y: 2.0, z: 3.0)

// Definir operaciones a realizar en el vector
def vectorOperations = {
    x += 2       // Incrementar el valor de x
    y *= 3       // Triplicar el valor de y
    z -= 1       // Decrementar el valor de z
    def mag = magnitude()  // Calcular la magnitud usando el método de Vector3D
    println "Magnitud del vector después de las operaciones: $mag"
}

// Aplicar las operaciones al vector
applyOperations(vector, vectorOperations)

// Imprimir el resultado
vector.print()
