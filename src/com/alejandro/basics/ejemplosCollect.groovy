package com.alejandro.basics

import groovy.transform.CompileStatic

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

enum Genero {
    MASCULINO, FEMENINO
}

class PersonaA {
    String nombre
    Integer edad
    Genero genero
}

@CompileStatic
class PersonGeneratorA {
    static List<PersonaA> generarPersonas(int cantidad) {
        def nombres = ["Alejandro", "Maria", "Juan", "Ana", "Pedro", "Lucia"]
        Random random = new Random()

        def generarPersona = { ->
            new PersonaA(
                    nombre: nombres[random.nextInt(nombres.size())],
                    edad: random.nextInt(100),
                    genero: Genero.values()[random.nextInt(Genero.values().length)]
            )
        }

        (1..cantidad).collect { generarPersona() }
    }
}

List<PersonaA> personas = PersonGeneratorA.generarPersonas(20)
personas.each { PersonaA it -> println "Nombre: ${it.nombre}, Edad: ${it.edad}, Género: ${it.genero}" }

class Pedido {
    String cliente
    LocalDate fecha
    BigDecimal total
}

@CompileStatic
class PedidoGenerator {
    static List<Pedido> generarPedidos(int cantidad) {
        def clientes = ["Cliente A", "Cliente B", "Cliente C", "Cliente D", "Cliente E"]
        Random random = new Random()

        def generarPedido = { ->
            new Pedido(
                    cliente: clientes[random.nextInt(clientes.size())],
                    fecha: LocalDate.now().minus(random.nextInt(365), ChronoUnit.DAYS),
                    total: BigDecimal.valueOf(random.nextDouble() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
        }

        (1..cantidad).collect { generarPedido() }
    }
}

// Ejemplo de uso
List<Pedido> pedidos = PedidoGenerator.generarPedidos(100)
pedidos.each { println "Cliente: ${it.cliente}, Fecha: ${it.fecha}, Total: ${it.total}" }

def totalPorCliente = pedidos.groupBy { it.cliente }
        .collectEntries { cliente, listaPedidos ->
            [cliente, listaPedidos*.total.sum()]
        }

totalPorCliente.each { cliente, total ->
    println "Cliente: ${cliente}, Total de pedidos: $total"
}

def totalPorMes = pedidos
        .groupBy { it.fecha.format(DateTimeFormatter.ofPattern("yyyy-MM")) }
        .collectEntries {
            mes, listaPedidos -> [mes, listaPedidos*.total.sum()]
        }


totalPorMes.each { mes, total ->
    println "Mes: ${mes}, Total de pedidos: $total"
}


enum Categoria {
    ELECTRONICA, ALIMENTACION, HOGAR, ROPA, JUGUETES
}

class Producto {
    String nombre
    Categoria categoria
    Integer stock
}


@CompileStatic
class ProductoGenerator {
    static List<Producto> generarProductos(int cantidad) {
        def nombres = ["Laptop", "Pan", "Silla", "Camiseta", "Muñeca"]
        Random random = new Random()

        def generarProducto = { ->
            new Producto(
                    nombre: nombres[random.nextInt(nombres.size())],
                    categoria: Categoria.values()[random.nextInt(Categoria.values().length)],
                    stock: random.nextInt(500) + 1 // Stock aleatorio entre 1 y 500
            )
        }

        (1..cantidad).collect { generarProducto() }
    }
}

// Ejemplo de uso
List<Producto> productos = ProductoGenerator.generarProductos(50)
productos.each { println "Nombre: ${it.nombre}, Categoría: ${it.categoria}, Stock: ${it.stock}" }

def mediaStockPorCategoria = productos
        .groupBy { it.categoria }
        .collectEntries {
            categoria, listaProductos ->
                def totalStock = listaProductos*.stock.sum()
                def count = listaProductos.size()
                return [categoria, totalStock / count]
        }

mediaStockPorCategoria.each { categoria, mediaStock ->
    println "Categoría: ${categoria}, Media de stock: $mediaStock"
}

