package com.alejandro.closures.delegation

import groovy.transform.Canonical

class ShoppingCart {

    List<Item> items = []
    PricingRules rules = new PricingRules()

    void addItem(String name, double price, int quantity = 1) {
        items << new Item(name, price, quantity)
    }

    void configurePricing(Closure closure) {
        closure.delegate = rules
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure()
    }

    double calculateTotal() {
        double subTotal = items.sum { it.price * it.quantity } as double
        return rules.applyRules(subTotal)
    }

}

@Canonical
class Item {
    String name
    double price
    int quantity
}

class PricingRules {
    double taxRate = 0.0
    double discountRate = 0.0
    double additionalFee = 0.0

    double applyRules(double subTotal) {
        double total = subTotal
        total += total * taxRate
        total -= total * discountRate
        total += additionalFee
        return total
    }
}

// Uso del sistema de carrito
def cart = new ShoppingCart()

// Agregar productos al carrito
cart.addItem("Laptop", 1500.00, 1)
cart.addItem("Mouse", 20.00, 2)
cart.addItem("Keyboard", 50.00, 1)

// Configurar las reglas de precios usando delegación
cart.configurePricing {
    taxRate = 0.08      // 8% de impuestos
    discountRate = 0.10 // 10% de descuento
    additionalFee = 15.00 // Una tarifa adicional fija de manejo/envío
}

// Mostrar el cálculo de totales
println "Subtotal: \$${cart.items.sum { it.price * it.quantity }}"
println "Total con impuestos, descuento y tarifas: \$${cart.calculateTotal().round(2)}"




