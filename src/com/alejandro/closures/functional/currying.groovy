package com.alejandro.closures.functional

/*
curry(..)
Permite crear una nueva _closure_ a partir de una existente, fijando algunos de los parámetros de la original.
Es útil para hacer _partial application_.
 */

def multiplicar = { a, b -> a * b }
def duplicar = multiplicar.curry(2)
println duplicar(5)// Resultado: 10 (equivalente a 2 * 5)

/*
### **`ncurry(..)`**
Similar a `curry(..)`, pero permite fijar un parámetro en una posición específica.
Esto es útil para fijar parámetros en una posición que no sea la primera.
*/
def sumar = { a, b, c -> a + b + c }
def sumarConBEnDos = sumar.ncurry(1, 2)// Fija el segundo argumento (índice 1) a 2
println sumarConBEnDos(1, 3)  // Resultado: 6 (equivalente a 1 + 2 + 3)

/*
### **`rcurry(..)`**
Fija parámetros en las últimas posiciones de la _closure_ en vez de las primeras.
Es útil para manipular funciones o _closures_ cuando los parámetros relevantes están al final.
* */
def dividir = { a, b -> a / b }
def mitad = dividir.rcurry(2)
println mitad(8)  // Resultado: 4 (equivalente a 8 / 2)


/////Ejemplo grande

/*
### **Ejemplo: Calcular estadísticas de ventas con clases**
Vamos a refactorizar nuestro ejemplo en tres partes principales:
1. Una clase que represente las operaciones de estadísticas.
2. Una clase que maneje los datos de las ventas.
3. Un metodo principal para ejecutar el flujo de operaciones.
* */

class Estadisticas {
    /**
     * Define las operaciones que se realizarán sobre los datos de ventas.
     * Cada operación es una closure.
     */
    static def suma = { ventas -> ventas.sum() }
    static def promedio = { ventas -> ventas.sum() / ventas.size() }
    static def maximo = { ventas -> ventas.max() }
}

class GestorDeVentas {
    /**
     * Clase que se encarga del manejo de las ventas organizadas por ciudad
     * y de aplicar cálculos sobre las mismas.
     */
    private final Map<String, List<Integer>> ventasPorCiudad

    /** Constructor */
    GestorDeVentas(Map<String, List<Integer>> ventasPorCiudad) {
        this.ventasPorCiudad = ventasPorCiudad
    }

    /**
     * Calcula estadísticas para todas las ciudades con base en una operación.
     * Esta función soporta currying para especializar las operaciones.
     */
    def calcularEstadisticas(Closure operacion, List<String> ciudadesFiltradas = null) {
        // Filtramos solo las ciudades indicadas (opcional)
        def datosFiltrados =
                ciudadesFiltradas ?
                        ventasPorCiudad.findAll { ciudad, _ -> ciudadesFiltradas.contains(ciudad) }
                        :
                        ventasPorCiudad

        // Aplicamos la operación a las ciudades filtradas
        datosFiltrados.collectEntries { ciudad, ventas -> [ciudad, operacion(ventas)] }
    }
}

class ProgramaPrincipal {
    /**
     * Clase principal que configura los datos, inicializa el gestor y ejecuta las operaciones.
     */
    static void main(String[] args){
        // Datos de ventas organizados por ciudad
        def datosDeVentas = [
                "Madrid": [200, 300, 400],
                "Barcelona": [150, 250, 350],
                "Valencia": [100, 200, 300]
        ]

        // Inicializamos el gestor de ventas
        def gestor = new GestorDeVentas(datosDeVentas)

        // Creamos closures especializadas con currying para sumar, promediar o calcular el máximo
        def calcularSuma = gestor.&calcularEstadisticas.curry(Estadisticas.suma)
        def calcularPromedio = gestor.&calcularEstadisticas.curry(Estadisticas.promedio)
        def calcularMaximo = gestor.&calcularEstadisticas.curry(Estadisticas.maximo)

        // Calculamos estadísticas para todas las ciudades
        println "Sumas por ciudad:"
        println calcularSuma()

        println "\nPromedios por ciudad:"
        println calcularPromedio()

        println "\nMáximos por ciudad:"
        println calcularMaximo()

        // Filtramos y calculamos para ciertas ciudades específicas
        def ciudadesFiltradas = ["Madrid", "Valencia"]
        println "\nSumas por ciudades filtradas ($ciudadesFiltradas):"
        println calcularSuma(ciudadesFiltradas)

        println "\nPromedios por ciudades filtradas ($ciudadesFiltradas):"
        println calcularPromedio(ciudadesFiltradas)
    }
}

