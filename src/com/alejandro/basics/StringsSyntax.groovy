package com.alejandro.basics

//String concatenation
assert 'ab' == 'a' + 'b';

//Triple-single-quoted string
def aMultilineString = """line one
line two
line three
"""

def startingAndEndingWithANewline = '''
line one
line two
line three
'''

/*You will notice that the resulting string contains a newline character as first character.
 It is possible to strip that character by escaping the newline with a backslash:*/
def strippedFirstNewline = '''\
line one
line two
line three
'''

assert !strippedFirstNewline.startsWith('\n');

println("variables:" +
        " $aMultilineString," +
        " $startingAndEndingWithANewline," +
        " $strippedFirstNewline")

//                  String interpolation

def name = 'Guillaume'  // a plain string
def greeting = "Hello ${name}"

assert greeting.toString() == 'Hello Guillaume'

def sum = "The sum of 2 and 3 equals ${2 + 3}"
assert sum.toString() == 'The sum of 2 and 3 equals 5'

//In addition to ${} placeholders, we can also use a lone $ sign prefixing a dotted expression:
def person = [name: 'Guillaume', age: 36];
assert "$person.name is $person.age years old" == 'Guillaume is 36 years old'


def number = 3.14
println "${number.toString()}"

//if the expression is ambiguous, you need to keep the curly braces:
String thing = 'treasure';
assert 'The x-coordinate of the treasure is represented by treasure.x' ==
        "The x-coordinate of the $thing is represented by ${thing}.x"  // <= Curly braces required

//interpolation with closure expressions
def sParameterLessClosure = "1 + 2 == ${-> 3}"
assert sParameterLessClosure == '1 + 2 == 3'

def sOneParamClosure = "1 + 2 == ${w -> w << 3}"
assert sOneParamClosure == '1 + 2 == 3'

number = 1;
def eagerGString = "value == ${number}"
def lazyGString = "value == ${-> number}"

assert eagerGString == "value == 1"
assert lazyGString == "value == 1"

number = 2
assert eagerGString == "value == 1"
assert lazyGString == "value == 2"
//But with a closure expression, the closure is called upon each coercion of the GString into String,
// resulting in an updated string containing the new number value


//Interoperability with Java
/*When a method (whether implemented in Java or Groovy) expects a java.lang.String, but we pass a groovy.lang.GString instance,
 the toString() method of the GString is automatically and transparently called.*/

String takeString(String message) {
    assert message instanceof String;
    return message;
}

def message = "The message is ${'hello'}";
assert message instanceof GString;

def result = takeString(message);
assert result instanceof String;
assert result == 'The message is hello'

//1 We create a GString variable
//2 We double-check it’s an instance of the GString
//3 We then pass that GString to a method taking a String as parameter
//4 The signature of the takeString() method explicitly says its sole parameter is a String
//5 We also verify that the parameter is indeed a String and not a GString.

//GString and String hashCodes
/*Although interpolated strings can be used in lieu of plain Java strings,
they differ with strings in a particular way: their hashCodes are different.
Plain Java strings are immutable, whereas the resulting String representation of a GString can vary,
depending on its interpolated values. Even for the same resulting string, GStrings and Strings don’t have the same hashCode.*/
assert "one: ${1}".hashCode() != "one: 1".hashCode()

//GString and Strings having different hashCode values!!!!!!!!!,
//using GString as Map keys should be avoided, especially if we try to retrieve an associated value with a String instead of a GString.
def key = "a"
def m = ["${key}": "letter ${key}"]
assert m["a"] == null

//1. The map is created with an initial pair whose key is a GString
//2. When we try to fetch the value with a String key, we will not find it, as Strings and GString have different hashCode values


//Triple-double-quoted string
name = 'Groovy';
def template = """
    Dear Mr ${name},

    You're the winner of the lottery!

    Yours sincerly,

    Dave
"""
assert template.toString().contains('Groovy')


//Slashy string

//Beyond the usual quoted strings, Groovy offers slashy strings, which use / as the opening and closing delimiter.
//Slashy strings are particularly useful for defining regular expressions and patterns, as there is no need to escape backslashes.
def fooPattern = /.*foo.*/;
assert fooPattern == '.*foo.*'

//Only forward slashes need to be escaped with a backslash:
def escapeSlash = /The character \/ is a forward slash/
assert escapeSlash == 'The character / is a forward slash'

//Slashy strings are multiline:
def multilineSlashy = /one
    two
    three/

assert multilineSlashy.contains('\n')

//Slashy strings can be thought of as just another way to define a GString but with different escaping rules.
//They hence support interpolation:
def color = 'blue'
def interpolatedSlashy = /a ${color} car/

assert interpolatedSlashy == 'a blue car'


//      Dollar slashy string


name = "Guillaume"
def date = "April, 1st"

def dollarSlashy = $/
    Hello $name,
    today we're ${date}.

    $ dollar sign
    $$ escaped dollar sign
    \ backslash
    / forward slash
    $/ escaped forward slash
    $$$/ escaped opening dollar slashy
    $/$$ escaped closing dollar slashy
/$

assert [
        'Guillaume',
        'April, 1st',
        '$ dollar sign',
        '$ escaped dollar sign',
        '\\ backslash',
        '/ forward slash',
        '/ escaped forward slash',
        '$/ escaped opening dollar slashy',
        '/$ escaped closing dollar slashy'
].every { dollarSlashy.contains(it) }

//      Characters

//Unlike Java, Groovy doesn’t have an explicit character literal. However,
//you can be explicit about making a Groovy string an actual character, by three different means:

char c1 = 'A';
assert c1 instanceof Character

def c2 = 'B' as char
assert c2 instanceof Character

def c3 = (char) 'C'
assert c2 instanceof Character