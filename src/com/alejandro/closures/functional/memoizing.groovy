package com.alejandro.closures.functional


// Definimos un cierre recursivo memorizado correctamente
def memoizedFibonacci = null // Declaramos la variable
memoizedFibonacci = { n ->
    if (n <= 1) return n
    return memoizedFibonacci(n - 1) + memoizedFibonacci(n - 2) // Referencia explícita al cierre memorizado
}.memoize()

// Llamada al cierre memorizado
println memoizedFibonacci(10) // Resultado: 55 (cálculo más rápido debido a la memorización)

def getUserInfo = { id ->
    println "Llamando al servidor para obtener información del usuario con ID: $id"
    sleep(1000)
    return "Usuario $id"
}.memoize()

println getUserInfo(1) // Primera vez, llama al servidor
println getUserInfo(1) // Subsecuente tiempo, usa el resultado en caché

def processData = { value ->
    println "Procesando $value"
    return value * 2
}.memoize()

[1, 2, 3, 2, 1].each { println processData(it) }


def factorial = { int n ->
    println "Calculando factorial de $n"
    return (1..n).inject(1) { acc, val -> acc * val }
}.memoize()

println factorial(5) // Calcula y almacena el resultado
println factorial(5) // Recupera el resultado de la caché
println factorial(6) // Calcula y almacena


def palabras = ["Hola", "mundo", "!"]
def resultado = palabras.inject("") { acc, val -> acc + val + " " }
println resultado.trim() // Resultado: "Hola mundo !"


/*
### **Escenario: Optimización en un sistema de estadísticas de usuarios**
Supongamos que tenemos un sistema que debe calcular estadísticas de usuarios de manera recurrente (como el total de visitas de los usuarios por región y ciudad). Sin embargo, realizar estos cálculos constantemente sobre la misma región no tiene sentido, ya que no siempre los datos cambian. Aquí es donde **memoize** entra en juego: almacenamos resultados previamente calculados para que el cálculo no tenga que realizarse nuevamente si los datos no han cambiado.
### **Diseño del ejemplo**
1. **Estructura de datos:**
    - Usaremos datos ficticios de visitas por ciudad y región.

2. **Clases:**
    - `Usuario`: Representa la entidad del usuario con sus visitas.
    - `GestorDeEstadisticas`: Contiene la lógica para computar y memorizar estadísticas complicadas mediante **memoize**.
    - `ProgramaPrincipal`: Maneja el flujo del programa como punto de entrada.

3. **Uso de memoize:**
    - Se usará en una _closure_ que realiza cálculos pesados sobre los datos (como el total de visitas).
* */

class Usuario {
    String ciudad
    int visitas

    Usuario(String ciudad, int visitas) {
        this.ciudad = ciudad
        this.visitas = visitas
    }
}

// Clase GestorDeEstadisticas que maneja los cálculos
class GestorDeEstadisticas {
    private List<Usuario> usuarios

    GestorDeEstadisticas(List<Usuario> usuarios) {
        this.usuarios = usuarios
    }

    /**
     * Closure que calcula las estadísticas pesadas (total de visitas por ciudad)
     * La optimizamos con memoize para evitar cálculos repetidos.
     */
    def calcularVisitasPorCiudad = { String ciudad ->
        println "Calculando visitas para la ciudad: $ciudad"
        usuarios.findAll { it.ciudad == ciudad }
                .sum { it.visitas } ?: 0
    }.memoize() // Utilizamos memoize aquí para guardar resultados previos

    /**
     * Closure que calcula las estadísticas por región (compuesta por varias ciudades)
     * Utiliza `calcularVisitasPorCiudad` para procesar las ciudades.
     */
    def calcularVisitasPorRegion = { List<String> ciudades ->
        println "Calculando visitas para región con ciudades: $ciudades"
        ciudades.sum { calcularVisitasPorCiudad(it) }// Llama a la closure memoizada
    }.memoize()
}

// Clase principal que gestiona el programa
class PrincipalProgram {
    static void main(String[] args) {
        // Datos de usuarios (ciudad y número de visitas)
        def usuarios = [
                new Usuario("Madrid", 200),
                new Usuario("Madrid", 300),
                new Usuario("Barcelona", 150),
                new Usuario("Barcelona", 250),
                new Usuario("Valencia", 100),
                new Usuario("Valencia", 200),
                new Usuario("Valencia", 300),
                new Usuario("Madrid", 400),
                new Usuario("Barcelona", 350)
        ]

        // Inicializamos el gestor de estadísticas
        def gestor = new GestorDeEstadisticas(usuarios)

        // Calcular estadísticas por ciudad
        println "\nEstadísticas iniciales por ciudad:"
        println "Madrid: ${gestor.calcularVisitasPorCiudad("Madrid")}"
        println "Barcelona: ${gestor.calcularVisitasPorCiudad("Barcelona")}"
        println "Valencia: ${gestor.calcularVisitasPorCiudad("Valencia")}"

        // Segunda vez, los resultados para las mismas ciudades se reutilizan (memoized)
        println "\nUsando memoize (sin recalculación):"
        println "Madrid: ${gestor.calcularVisitasPorCiudad("Madrid")}"
        println "Barcelona: ${gestor.calcularVisitasPorCiudad("Barcelona")}"

        // Calcular estadísticas por región (compuesta por varias ciudades)
        println "\nEstadísticas por región (Madrid y Valencia):"
        println "Región Madrid-Valencia: ${gestor.calcularVisitasPorRegion(["Madrid", "Valencia"])}"

        println "\nVolviendo a calcular (memoize optimizado):"
        println "Región Madrid-Valencia: ${gestor.calcularVisitasPorRegion(["Madrid", "Valencia"])}"

        // Cálculo en otra región
        println "\nEstadísticas por una nueva región (España):"
        println "Región España (todas las ciudades): ${gestor.calcularVisitasPorRegion(["Madrid", "Barcelona", "Valencia"])}"

        // Segunda vez con memoize
        println "\nVolviendo a calcular España (memoize optimizado):"
        println "Región España: ${gestor.calcularVisitasPorRegion(["Madrid", "Barcelona", "Valencia"])}"
    }

}