package com.alejandro.closures.delegation

// Clase que representa la configuración de la aplicación
class AppConfig {
    String name
    String version
    int maxUsers

    void printConfig() {
        println "Configuration:"
        println "Name: $name"
        println "Version: $version"
        println "Max Users: $maxUsers"
    }
}

// DSL para la configuración de la aplicación
class AppConfigurer {
    AppConfig config = new AppConfig()

    void configure(Closure closure) {
        closure.delegate = config  // Aquí es donde 'delegate' se usa
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }
}

// Uso de la DSL para configurar la aplicación
def appConfigurator = new AppConfigurer()
appConfigurator.configure {
    name = "MiAplicacion"
    version = "1.0"
    maxUsers = 100
}

// Imprimir configuración final
appConfigurator.config.printConfig()

