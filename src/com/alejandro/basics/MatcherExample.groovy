package com.alejandro.basics
// Definir el patrón que queremos buscar
def pattern = "\\b\\w{4,}\\b" // Este patrón busca palabras de 4 o más letras
def p = ~pattern

// Lista de archivos simulada, cada archivo es una lista de párrafos
def files = [
        ["Groovy is an object-oriented programming language for the Java platform.",
         "It is a dynamic language with features similar to those of Python, Ruby, and Smalltalk."]
        ,
        ["Another paragraph in another file.",
         "Groovy can be used as both a scripting language and a programming language."]
]

// Función para encontrar las palabras que cumplen el patrón en un texto dado
def findMatchingWords = { text ->
    def matcher = p.matcher(text)
    def matches = []
    while (matcher.find()) {
        matches << matcher.group()
    }
    return matches
}

// Procesar cada párrafo en cada archivo para encontrar coincidencias
def allMatches = files.collect { file ->
    file.collect { paragraph ->
        findMatchingWords(paragraph)
    }
}

// Aplanar la lista de coincidencias
def flattenedMatches = allMatches.flatten()

// Imprimir todas las coincidencias encontradas
println("Ocurrencias de palabras con 4 o más letras en todos los archivos:")
println(flattenedMatches)
