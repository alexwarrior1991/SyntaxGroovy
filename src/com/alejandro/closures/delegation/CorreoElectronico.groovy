package com.alejandro.closures.delegation

// Clase que representa un correo electrónico
class Email {
    String from
    String to
    String subject
    String body

    void send() {
        println "Enviando correo de: $from a: $to"
        println "Asunto: $subject"
        println "Cuerpo: $body"
    }
}

// Uso del DSL para configurar y enviar un correo electrónico
def sendEmail(Closure closure) {
    Email email = new Email()
    closure.delegate = email
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure()
    email.send()
}

// Configuración del correo mediante el DSL
sendEmail {
    from = "juan@example.com"
    to = "maria@example.com"
    subject = "Saludos"
    body = "Hola María, espero que estés bien."
}
