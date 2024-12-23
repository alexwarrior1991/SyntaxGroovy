package com.alejandro.closures.delegation

// Clase para representar un botón en la GUI
class Button {
    String label
    Closure onClick

    void click() {
        if (onClick) {
            onClick()
        } else {
            println "Button '$label' clicked!"
        }
    }
}

// Clase para representar una ventana en la GUI
class Window {
    String title
    int width
    int height
    List<Button> buttons = []

    void button(String label, Closure closure) {
        Button button = new Button(label: label)
        closure.delegate = button
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
        buttons.add(button)
    }

    void show() {
        println "Showing window titled: '$title' with dimensions ${width}x${height}"
        buttons.each { button ->
            println "Button: ${button.label}"
        }
    }
}

// Builder para crear la GUI
class GUIBuilder {
    Window window = new Window()

    void window(String title, int width, int height, Closure closure) {
        window.title = title
        window.width = width
        window.height = height
        closure.delegate = window
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }
}

// Uso del builder para crear una GUI con más configuraciones y funcionalidades
def builder = new GUIBuilder()
builder.window("My Application", 800, 600) {
    button("OK") {
        onClick = { println "OK Button Pressed" }
    }
    button("Cancel") {
        onClick = { println "Cancel Button Pressed" }
    }
}

// Mostrar la ventana construida y simular clics en los botones
builder.window.show()
builder.window.buttons.each { button ->
    button.click()
}