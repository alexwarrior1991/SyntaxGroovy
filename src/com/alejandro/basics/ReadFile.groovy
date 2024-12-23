package com.alejandro.basics

import groovy.transform.ToString

// Clase para representar un Producto
@ToString
class Product {
    int id
    String name
    BigDecimal price
    int quantity
}

def readCSV(String filePath) {
    def products = []
    def file = new File(filePath)

    // Validar si el archivo existe
    if (!file.exists()) {
        println "El archivo no existe: $filePath"
        return products
    }

    // Leer y procesar el archivo línea por línea
    file.eachLine { line, lineNumber ->
        if (lineNumber == 1) return // Saltar la línea de cabecera

        def fields = line.split(",")
        if (fields.size() >= 4) {
            try {
                // Crear un objeto Producto con los datos del CSV
                def product = new Product(
                        id: fields[0].toInteger(),
                        name: fields[1].trim(),
                        price: new BigDecimal(fields[2].trim()),
                        quantity: fields[3].toInteger()
                )
                products << product // Agregar el producto a la lista
            } catch (Exception e) {
                println "Error al procesar la línea ${lineNumber}: ${line}"
            }
        } else {
            println "Línea mal formateada en ${lineNumber}: ${line}"
        }
    }
    return products
}

// Ruta relativa
def currentDir = new File("").absolutePath
def filePath = "$currentDir/files/products.csv"

println "Buscando archivo en: $filePath"

// Verificar si el archivo existe
def file = new File(filePath)
if (!file.exists()) {
    println "El archivo no existe en: $filePath"
} else {
    println "Archivo encontrado. Procesando..."
    def products = readCSV(filePath)
    products.each { println it }
}