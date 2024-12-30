package com.alejandro.patterns

import java.util.function.Function

class Logger {
    def log(String message) {
        println message
    }
}

class TimeStampingLogger extends Logger {
    private Logger logger

    TimeStampingLogger(logger) {
        this.logger = logger
    }

    def log(String message) {
        def now = Calendar.instance
        logger.log("$now.time: $message")
    }
}

class UpperLogger extends Logger {
    private Logger logger

    UpperLogger(logger) {
        this.logger = logger
    }

    def log(String message) {
        logger.log(message.toUpperCase())
    }
}

//We can use the decorators like so:
def logger = new UpperLogger(new TimeStampingLogger(new Logger()))
logger.log("G'day Mate")
// => Tue May 22 07:13:50 EST 2007: G'DAY MATE

logger = new TimeStampingLogger(new UpperLogger(new Logger()))
logger.log('Hi There')
// => TUE MAY 22 07:13:50 EST 2007: HI THERE

//Simplifying with closures or lambdas

class DecoratingLogger {
    def decoration = Closure.IDENTITY

    def log(String message) {
        println decoration(message)
    }
}

def upper = { it.toUpperCase() }
def stamp = { "$Calendar.instance.time: $it" }
/*
stamp << upper equivalente a
def composedClosure = { input ->
    stamp(upper(input))
}
* */
logger = new DecoratingLogger(decoration: stamp << upper)
logger.log("G'day Mate")
// Sat Aug 29 15:28:29 AEST 2020: G'DAY MATE

//We can use the same approach with lambdas:
class DecoratingLambdaLogger {
    Function<String, String> decoration = Function.identity()

    def log(String message) {
        println decoration.apply(message)
    }
}

Function<String, String> upperLambda = s -> s.toUpperCase()
Function<String, String> stampLambda = s -> "$Calendar.instance.time: $s"
logger = new DecoratingLambdaLogger(decoration: upperLambda.andThen(stamp))
logger.log("G'day Mate")
// => Sat Aug 29 15:38:28 AEST 2020: G'DAY MATE

//A touch of dynamic behaviour

//Our previous decorators were specific to Logger objects. We can use Groovy’s Meta-Object Programming capabilities to
//create a decorator which is far more general purpose in nature. Consider this class:

class GenericLowerDecorator {
    private delegate

    GenericLowerDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
        def newargs = args.collect { arg ->
            if (arg instanceof String) {
                return arg.toLowerCase()
            } else {
                return arg
            }
        }
        delegate.invokeMethod(name, newargs)
    }
}

logger = new GenericLowerDecorator(new TimeStampingLogger(new Logger()))
logger.log('IMPORTANT Message')
// => Tue May 22 07:27:18 EST 2007: important message

//More dynamic decorating

class Calc {
    def add(a, b) { a + b }
}

class TracingDecorator {
    private delegate

    TracingDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
        println "Calling $name$args"
        def before = System.currentTimeMillis()
        def result = delegate.invokeMethod(name, args)
        println "Got $result in ${System.currentTimeMillis() - before} ms"
        result
    }
}

def tracedCalc = new TracingDecorator(new Calc())
assert 15 == tracedCalc.add(3, 12)

// Clase base que implementa el cálculo principal
class PriceCalculator {
    def calculatePrice(basePrice) {
        basePrice
    }
}

// Decorador para añadir trazas al cálculo e inyectar closures para lógica de descuento
class TracingDiscountDecorator {
    private delegate
    private closure = { it } // Closure para personalizar el descuento (por defecto no hace nada)

    // Constructor
    TracingDiscountDecorator(delegate, closure) {
        this.delegate = delegate
        this.closure = closure
    }

    def calculatePrice(basePrice) {
        println "Calculando el precio base: $basePrice"
        def before = System.currentTimeMillis()

        // Llamada al servicio base o delegador previo
        def price = delegate.calculatePrice(basePrice)

        // Aplicación del descuento a través del closure
        def discountedPrice = closure(price)
        println "Resultado: $discountedPrice (descuento aplicado) en ${System.currentTimeMillis() - before} ms"

        discountedPrice
    }
}

// Ejemplo de closures para aplicar descuentos específicos
def seasonalDiscount = { price -> price * 0.90 } // 10% de descuento por temporada
def bulkDiscount = { price -> price > 1000 ? price * 0.85 : price } // 15% si el monto es mayor a 1000
def couponDiscount = { price -> price - 50 } // Aplicar un descuento fijo de 50

// Crear un servicio base
def baseCalculator = new PriceCalculator()

// Encadenar decoradores con descuentos dinámicos
def discountedCalculator = new TracingDiscountDecorator(baseCalculator, seasonalDiscount)
discountedCalculator = new TracingDiscountDecorator(discountedCalculator, bulkDiscount)
discountedCalculator = new TracingDiscountDecorator(discountedCalculator, couponDiscount)

// Ejecutar el cálculo sobre diferentes precios
println "\nEjemplo 1:"
println "Precio final: ${discountedCalculator.calculatePrice(2000)}"

println "\nEjemplo 2:"
println "Precio final: ${discountedCalculator.calculatePrice(900)}"