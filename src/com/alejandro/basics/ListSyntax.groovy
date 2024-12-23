package com.alejandro.basics

/*
Groovy uses a comma-separated list of values, surrounded by square brackets, to denote lists.
Groovy lists are plain JDK java.util.List, as Groovy doesn’t define its own collection classes.
The concrete list implementation used when defining list literals are java.util.ArrayList by default,
 unless you decide to specify otherwise, as we shall see later on.
*/

def numbers = [1, 2, 3];

assert numbers instanceof List;
assert numbers.size() == 3

//We define a list numbers delimited by commas and surrounded by square brackets, and we assign that list into a variable
//The list is an instance of Java’s java.util.List interface
//The size of the list can be queried with the size() method, and shows our list contains 3 elements

//We mentioned that by default, list literals are actually instances of java.util.ArrayList,
//but it is possible to use a different backing type for our lists,
//thanks to using type coercion with the as operator, or with explicit type declaration

def arrayList = [1, 2, 3]
assert arrayList instanceof ArrayList

def linkedList = [2, 3, 4] as LinkedList
assert linkedList instanceof LinkedList

LinkedList otherLinked = [3, 4, 5]
assert otherLinked instanceof LinkedList

//We use coercion with the as operator to explicitly request a java.util.LinkedList implementation
//We can say that the variable holding the list literal is of type java.util.LinkedList

def letters = ['a', 'b', 'c', 'd']

assert letters[0] == 'a'
assert letters[1] == 'b'

assert letters[-1] == 'd'
assert letters[-2] == 'c'

letters[2] = 'C'
assert letters[2] == 'C'

letters << 'e'
assert letters[4] == 'e'
assert letters[-1] == 'e'

assert letters[1, 3] == ['b', 'd']
assert letters[2..4] == ['C', 'd', 'e']

//Access the first element of the list (zero-based counting)
//Access the last element of the list with a negative index: -1 is the first element from the end of the list
//Use an assignment to set a new value for the third element of the list
//Use the << leftShift operator to append an element at the end of the list
//Access two elements at once, returning a new list containing those two elements
//Use a range to access a range of values from the list, from a start to an end element position

//As lists can be heterogeneous in nature, lists can also contain other lists to create multidimensional lists:

def multi = [[0, 1], [2, 3]]
assert multi[1][0] == 2

//Define a list of numbers
//Access the second element of the top-most list, and the first element of the inner list


//Arrays

//Groovy reuses the list notation for arrays, but to make such literals arrays,
//        you need to explicitly define the type of the array through coercion or type declaration.

String[] arrStr = ['Ananas', 'Banana', 'Kiwi']

assert arrStr instanceof String[]
assert !(arrStr instanceof List)

def numArr = [1, 2, 3] as int[]

assert numArr instanceof int[]
assert numArr.size() == 3

//Define an array of strings using explicit variable type declaration
//Assert that we created an array of strings
//Create an array of ints with the as operator
//Assert that we created an array of primitive ints

//You can also create multi-dimensional arrays:

def matrix3 = new Integer[3][3]
assert matrix3.size() == 3

Integer[][] matrix2
matrix2 = [[1, 2], [3, 4]]
assert matrix2 instanceof Integer[][]

//You can define the bounds of a new array
//Or declare an array without specifying its bounds

//Access to elements of an array follows the same notation as for lists:

String[] names = ['Cédric', 'Guillaume', 'Jochen', 'Paul']
assert names[0] == 'Cédric'

names[2] = 'Blackdrag'
assert names[2] == 'Blackdrag'

def primes = new int[]{2, 3, 5, 7, 11}
assert primes.size() == 5 && primes.sum() == 28
assert primes.class.name == '[I'

def pets = new String[]{'cat', 'dog'}
assert pets.size() == 2 && pets.sum() == 'catdog'
assert pets.class.name == '[Ljava.lang.String;'

// traditional Groovy alternative still supported
String[] groovyBooks = ['Groovy in Action', 'Making Java Groovy']
assert groovyBooks.every { it.contains('Groovy') }

//Maps

def colors = [red: '#FF0000', green: '#00FF00', blue: '#0000FF']

assert colors['red'] == '#FF0000'
assert colors.green == '#00FF00'

colors['pink'] = '#FF00FF'
colors.yellow = '#FFFF00'

assert colors.pink == '#FF00FF'
assert colors['yellow'] == '#FFFF00'

assert colors instanceof LinkedHashMap

//We define a map of string color names, associated with their hexadecimal-coded html colors
//We use the subscript notation to check the content associated with the red key
//We can also use the property notation to assert the color green’s hexadecimal representation
//Similarly, we can use the subscript notation to add a new key/value pair
//Or the property notation, to add the yellow color

//If you try to access a key which is not present in the map:

assert colors.unknown == null

def emptyMap = [:]
assert emptyMap.anyKey == null

//You will retrieve a null result.

//In the examples above, we used string keys, but you can also use values of other types as keys:
def numberss = [1: 'one', 2: 'two']

assert numberss[1] == 'one'

//Here, we used numbers as keys, as numbers can unambiguously be recognized as numbers,
//so Groovy will not create a string key like in our previous examples.
//But consider the case you want to pass a variable in lieu of the key, to have the value of that variable become the key:

def key = 'name'
def person = [key: 'Guillaume']
assert !person.containsKey('name')
assert person.containsKey('key')


//The key associated with the 'Guillaume' name will actually be the "key" string, not the value associated with the key variable
//The map doesn’t contain the 'name' key
//Instead, the map contains a 'key' key

//When you need to pass variable values as keys in your map definitions,
//you must surround the variable or expression with parentheses:

person = [(key): 'Guillaume']

assert person.containsKey('name')
assert !person.containsKey('key')

//This time, we surround the key variable with parentheses, to instruct the parser we are passing a variable rather than defining a string key
//The map does contain the name key
//But the map doesn’t contain the key key as before
