package com.alejandro.closures.delegation

class DatabaseConfig {
    ServerConfig serverConfig = new ServerConfig()
    CredentialsConfig credentialsConfig = new CredentialsConfig()
    ConnectionProperties connectionProperties = new ConnectionProperties()

    void server(Closure closure) {
        closure.delegate = serverConfig
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    void credentials(Closure closure) {
        closure.delegate = credentialsConfig
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    void properties(Closure closure) {
        closure.delegate = connectionProperties
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    void showConfig() {
        println "\n--- Base de Datos Configuración ---"
        println "Servidor:"
        println "  Host: ${serverConfig.host}"
        println "  Puerto: ${serverConfig.port}"
        println "  Tipo: ${serverConfig.dbType}"
        println "Credenciales:"
        println "  Usuario: ${credentialsConfig.username}"
        println "  Contraseña: ${credentialsConfig.password}"
        println "Propiedades de conexión:"
        println "  Timeout: ${connectionProperties.timeout}s"
        println "  Pool Size: ${connectionProperties.poolSize}"
        println "----------------------------------\n"
    }
}

class ServerConfig {
    String host
    int port
    String dbType
}

class CredentialsConfig {
    String username
    String password
}

class ConnectionProperties {
    int timeout
    int poolSize
}

// Builder del sistema de configuración
class DatabaseConfigBuilder {
    DatabaseConfig config = new DatabaseConfig()

    void configure(Closure closure) {
        closure.delegate = config
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }
}

// Uso del sistema de configuración
def dbConfigBuilder = new DatabaseConfigBuilder()

dbConfigBuilder.configure {
    server {
        host = "localhost"
        port = 5432
        dbType = "PostgreSQL"
    }
    credentials {
        username = "admin"
        password = "password123"
    }
    properties {
        timeout = 30
        poolSize = 10
    }
}

// Mostrar la configuración resultante
dbConfigBuilder.config.showConfig()
