package com.alejandro.patterns

/*
The Command Pattern is a pattern for loosely coupling a client object which wants to execute a series of commands and
receiver objects which enact those commands. Instead of talking to receivers directly,
clients interact with an intermediary object which then relays the necessary commands to the receivers.
The pattern is in common use within the JDK, for example the api:javax.swing.Action[] class in Swing decouples swing code from receivers like buttons, menu items and panels.
* */


// Asegúrate de declarar explícitamente como "public"
public interface Command {
    void execute()
}

// Invoker class
class Switch {
    private final Map<String, Command> commandMap = new HashMap<>()

    void register(String commandName, Command command) {
        commandMap[commandName] = command
    }

    void execute(String commandName) {
        Command command = commandMap[commandName]
        if (!command) {
            throw new IllegalStateException("No command registered for " + commandName)
        }
        command.execute()
    }
}

// Receiver class
class Light {
    void turnOn() {
        println "The light is on"
    }

    void turnOff() {
        println "The light is off"
    }
}

class Door {
    static void unlock() {
        println 'The door is unlocked'
    }
}

class SwitchOnCommand implements Command {
    Light light

    @Override
    // Command
    void execute() {
        light.turnOn()
    }
}

class SwitchOffCommand implements Command {
    Light light

    @Override
    // Command
    void execute() {
        light.turnOff()
    }
}

// Ejecución del patrón
Light lamp = new Light()
Command switchOn = new SwitchOnCommand(light: lamp)
Command switchOff = new SwitchOffCommand(light: lamp)

Switch mySwitch = new Switch()
// Usar funciones de alta precisión de Groovy para simplificar
mySwitch.register("on", { -> lamp.turnOn() } as Command)
mySwitch.register("off", { -> lamp.turnOff() } as Command)

//mySwitch.register("on", switchOn)
//mySwitch.register("off", switchOff)

mySwitch.execute("on")
mySwitch.execute("off")


//We can simplify further using the JDK’s existing Runnable interface and using a switch map rather than a separate Switch class as shown here:

Map<String, Runnable> mySwitch2 = [
        on    : lamp::turnOn,
        off   : lamp::turnOff,
        unlock: Door::unlock
]

mySwitch2.on()
mySwitch2.off()
mySwitch2.unlock()


//As a variation, if the command names aren’t important to us, we can forgo using the switch map and just have a list of tasks to invoke as shown here:
List<Runnable> tasks = [lamp::turnOn, lamp::turnOff, Door::unlock]
tasks.each { it.run() }