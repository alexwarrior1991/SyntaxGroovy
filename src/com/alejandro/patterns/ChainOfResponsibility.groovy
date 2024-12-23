package com.alejandro.patterns
/*
In the Chain of Responsibility Pattern, objects using and implementing an interface (one or more methods) are intentionally
loosely coupled. A set of objects that implement the interface are organised in a list (or in rare cases a tree).
Objects using the interface make requests from the first implementor object. It will decide whether to perform any
action itself and whether to pass the request further down the line in the list (or tree). Sometimes a default
implementation for some request is also coded into the pattern if none of the implementors respond to the request.
*/

//Example using traditional classes
class UnixLister {
    private nextInLine

    UnixLister(next) { nextInLine = next }

    def listFiles(dir) {
        if (System.getProperty('os.name') == 'Linux') {
            println "ls $dir".execute().text
        } else {
            nextInLine.listFiles(dir)
        }
    }
}

class WindowsLister {
    private nextInLine

    WindowsLister(next) { nextInLine = next }

    def listFiles(dir) {
        if (System.getProperty('os.name').startsWith('Windows')) {
            println "cmd.exe /c dir $dir".execute().text
        } else {
            nextInLine.listFiles(dir)
        }
    }
}

class DefaultLister {
    def listFiles(dir) {
        new File(dir).eachFile { f -> println f }
    }
}

def lister = new UnixLister(new WindowsLister(new DefaultLister()))

lister.listFiles('Downloads')

//Example using simplifying strategies

//For simple cases, consider simplifying your code by not requiring the chain of classes. Instead, use Groovy truth and the elvis operator as shown here:

String unixListFiles(dir) {
    if (System.getProperty('os.name') == 'Linux') {
        "ls $dir".execute().text
    }
}

String windowsListFiles(dir) {
    if (System.getProperty('os.name').startsWith('Windows')) {
        "cmd.exe /c dir $dir".execute().text
    }
}

String defaultListFiles(dir) {
    new File(dir).listFiles().collect { f -> f.name }.join('\\n')
}

def dir = 'Downloads'

println unixListFiles(dir) ?: windowsListFiles(dir) ?: defaultListFiles(dir)

//Or Groovy’s switch as shown here:
String listFiles(dir) {
    switch (dir) {
        case { System.getProperty('os.name') == 'Linux' }:
            return "ls $dir".execute().text
        case { System.getProperty('os.name').startsWith('Windows') }:
            return "cmd.exe /c dir $dir".execute().text
        default:
            new File(dir).listFiles().collect { f -> f.name }.join('\\n')
    }
}

println listFiles('Downloads')

//Alternatively, for Groovy 3+, consider using streams of lambdas as shown here:

Optional<String> unixListFiles(String dir) {
    Optional.ofNullable(dir)
            .filter(d -> System.getProperty('os.name') == 'Linux')
            .map(d -> "ls $d".execute().text)
}

Optional<String> windowsListFiles(String dir) {
    Optional.ofNullable(dir)
            .filter(d -> System.getProperty('os.name').startsWith('Windows'))
            .map(d -> "cmd.exe /c dir $d".execute().text)
}

Optional<String> defaultListFiles(String dir) {
    Optional.ofNullable(dir)
            .map(d -> new File(d).listFiles().collect { f -> f.name }.join('\\n'))
}

def handlers = [this::unixListFiles, this::windowsListFiles, this::defaultListFiles]

println handlers.stream()
        .map(f -> f(dir))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst()
        .get()