package com.alejandro.patterns

/*
The Bouncer Pattern describes usage of a method whose sole purpose is to either throw an exception (when particular conditions hold) or do nothing. Such methods are often used to defensively guard pre-conditions of a method.

When writing utility methods, you should always guard against faulty input arguments. When writing internal methods, you may be able to ensure that certain pre-conditions always hold by having sufficient unit tests in place. Under such circumstances, you may reduce the desirability to have guards on your methods.

Groovy differs from other languages in that you frequently use the assert method within your methods rather than having a large number of utility checker methods or classes.
* */

//Null Checking Example

class NullChecker {
    static check(name, arg) {
        if (arg == null) {
            throw new IllegalArgumentException(name + ' is null')
        }
    }
}

//And we would use it like this:

//void doStuff(String name, Object value) {
//    NullChecker.check('name', name)
//    NullChecker.check('value', value)
//    // do stuff
//}

//But a more Groovy way to do this would simply be like this:

void doStuff(String name, Object value) {
    assert name != null, 'name should not be null'
    assert value != null, 'value should not be null'
    // do stuff
}

//Validation Example

class NumberChecker {
    static final String NUMBER_PATTERN = "\\\\d+(\\\\.\\\\d+(E-?\\\\d+)?)?"
    static isNumber(str) {
        if (!str ==~ NUMBER_PATTERN) {
            throw new IllegalArgumentException("Argument '$str' must be a number")
        }
    }
    static isNotZero(number) {
        if (number == 0) {
            throw new IllegalArgumentException('Argument must not be 0')
        }
    }
}

//And we would use it like this:
def stringDivide(String dividendStr, String divisorStr) {
    NumberChecker.isNumber(dividendStr)
    NumberChecker.isNumber(divisorStr)
    def dividend = dividendStr.toDouble()
    def divisor = divisorStr.toDouble()
    NumberChecker.isNotZero(divisor)
    dividend / divisor
}

println stringDivide('1.2E2', '3.0')
// => 40.0

//But with Groovy we could just as easily use:
def stringDivideG(String dividendStr, String divisorStr) {
    assert dividendStr =~ NumberChecker.NUMBER_PATTERN
    assert divisorStr =~ NumberChecker.NUMBER_PATTERN
    def dividend = dividendStr.toDouble()
    def divisor = divisorStr.toDouble()
    assert divisor != 0, 'Divisor must not be 0'
    dividend / divisor
}