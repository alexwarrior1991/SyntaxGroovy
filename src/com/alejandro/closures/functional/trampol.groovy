package com.alejandro.closures.functional

/*
### **¿Qué es trampoline?**
El método **`trampoline`** en Groovy es una técnica utilizada para evitar problemas de desbordamiento de la pila (_stack overflow_) que pueden ocurrir en funciones recursivas profundas.
En Groovy, **`trampoline`** convierte una llamada recursiva regular en una llamada recursiva que usa el estilo
de "retorno por iteración" (_tail-call optimization_) para eliminar la
necesidad de mantener un rastro de la pila de llamadas. De esta manera, en lugar de apilar varias llamadas recursivas, las funciones se van resolviendo como si estuvieran en un solo nivel (simulando un bucle).
* */

def factorialSinTrampoline(n, acumulado = 1) {
    if (n <= 1) {
        return acumulado
    } else {
        return factorialSinTrampoline(n - 1, n * acumulado)
    }
}

// Llamada regular (puede fallar si n es muy grande)
println factorialSinTrampoline(5) // 120
println factorialSinTrampoline(5000) // ¡StackOverflowError para valores grandes!

// Definimos una closure recursiva
def factorialConTrampoline
factorialConTrampoline = { n, acumulado = 1 ->
    if (n <= 1) {
        return acumulado
    } else {
        return factorialConTrampoline.trampoline(n - 1, n * acumulado) // Aquí usamos trampoline
    }
}.trampoline() // Transformamos la closure en un "trampoline"

println factorialConTrampoline(5)    // 120
println factorialConTrampoline(5000) // ¡Funciona sin StackOverflowError!

/*
1.
    - Cuando llamamos a `factorialConTrampoline.trampoline()`, Groovy regresará inmediatamente al nivel de ejecución principal sin apilar llamadas adicionales en la pila.
    - En cada nivel de recursión, `trampoline` trata la siguiente llamada recursiva como un nuevo ciclo en un bucle.

**Por qué evita errores de pila:**
    - En lugar de apilar múltiples llamadas recursivas, las llamadas se "suspenden" hasta que se evalúan secuencialmente.
* */

def fibonacciSinTrampoline(int n) {
    if (n <= 1) {
        return n
    } else {
        return fibonacciSinTrampoline(n - 1) + fibonacciSinTrampoline(n - 2)
    }
}

println fibonacciSinTrampoline(10) // 55
// println fibonacciSinTrampoline(50) // ¡Muy lento e ineficiente!


// Closure de Fibonacci optimizada con trampoline
def fibonacciConTrampoline
fibonacciConTrampoline = { int n, BigInteger a = 0, BigInteger b = 1 ->
    if (n == 0) {
        return a
    } else if (n == 1) {
        return b
    } else {
        return fibonacciConTrampoline.trampoline(n - 1, b, a + b) // Trampoline para optimizar
    }
}.trampoline()

// Pruebas
println fibonacciConTrampoline(10)  // 55
println fibonacciConTrampoline(50)  // 12586269025
println fibonacciConTrampoline(500) // ¡Funciona perfectamente sin problemas de pila!