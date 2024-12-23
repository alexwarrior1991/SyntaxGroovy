package com.alejandro.basics

/*Integral literals*/

//You can create integral numbers of those types with the following declarations:
// primitive types
{
    byte b = 1
    char c = 2
    short s = 3
    int i = 4
    long l = 5
}


// infinite precision
BigInteger bi = 6

//If you use optional typing by using the def keyword, the type of the integral number will vary:
//it’ll adapt to the capacity of the type that can hold that number.

def a = 1
assert a instanceof Integer

// Integer.MAX_VALUE
def b = 2147483647
assert b instanceof Integer

// Integer.MAX_VALUE + 1
def c = 2147483648
assert c instanceof Long

// Long.MAX_VALUE
def d = 9223372036854775807
assert d instanceof Long

// Long.MAX_VALUE + 1
def e = 9223372036854775808
assert e instanceof BigInteger

def na = -1
assert na instanceof Integer

// Integer.MIN_VALUE
def nb = -2147483648
assert nb instanceof Integer

// Integer.MIN_VALUE - 1
def nc = -2147483649
assert nc instanceof Long

// Long.MIN_VALUE
def nd = -9223372036854775808
assert nd instanceof Long

// Long.MIN_VALUE - 1
def ne = -9223372036854775809
assert ne instanceof BigInteger

// Alternative non-base 10 representations

//Binary numbers start with a 0b prefix:
{
    int xInt = 0b10101111
    assert xInt == 175

    short xShort = 0b11001001
    assert xShort == 201 as short

    byte xByte = 0b11
    assert xByte == 3 as byte

    long xLong = 0b101101101101
    assert xLong == 2925l

    BigInteger xBigInteger = 0b111100100001
    assert xBigInteger == 3873g

    int xNegativeInt = -0b10101111
    assert xNegativeInt == -175
}

//Octal numbers are specified in the typical format of 0 followed by octal digits.
{
    int xInt = 077
    assert xInt == 63

    short xShort = 011
    assert xShort == 9 as short

    byte xByte = 032
    assert xByte == 26 as byte

    long xLong = 0246
    assert xLong == 166l

    BigInteger xBigInteger = 01111
    assert xBigInteger == 585g

    int xNegativeInt = -077
    assert xNegativeInt == -63
}

//Hexadecimal numbers are specified in the typical format of 0x followed by hex digits.
{
    int xInt = 0x77
    assert xInt == 119

    short xShort = 0xaa
    assert xShort == 170 as short

    byte xByte = 0x3a
    assert xByte == 58 as byte

    long xLong = 0xffff
    assert xLong == 65535l

    BigInteger xBigInteger = 0xaaaa
    assert xBigInteger == 43690g

    Double xDouble = new Double('0x1.0p0')
    assert xDouble == 1.0d

    int xNegativeInt = -0x77
    assert xNegativeInt == -119
}

//Decimal literals

//You can create decimal numbers of those types with the following declarations:

{
    // primitive types
    float f = 1.234
    double doub = 2.345

// infinite precision
    BigDecimal bd = 3.456
}

//Decimals can use exponents, with the e or E exponent letter, followed by an optional sign, and an integral number representing the exponent:

assert 1e3 == 1_000.0
assert 2E4 == 20_000.0
assert 3e+1 == 30.0
assert 4E-2 == 0.04


//Underscore in literals
/*When writing long literal numbers, it’s harder on the eye to figure out how some numbers are grouped together,
for example with groups of thousands, of words, etc.
By allowing you to place underscore in number literals, it’s easier to spot those groups:*/

long creditCardNumber = 1234_5678_9012_3456L
long socialSecurityNumbers = 999_99_9999L
double monetaryAmount = 12_345_132.12
long hexBytes = 0xFF_EC_DE_5E
long hexWords = 0xFFEC_DE5E
long maxLong = 0x7fff_ffff_ffff_ffffL
long alsoMaxLong = 9_223_372_036_854_775_807L
long bytes = 0b11010010_01101001_10010100_10010010


//We can force a number (including binary, octals and hexadecimals) to have a specific type by giving a suffix (see table below),
//either uppercase or lowercase.

assert 42I == Integer.valueOf('42')
assert 42i == Integer.valueOf('42') // lowercase i more readable
assert 123L == Long.valueOf("123") // uppercase L more readable
assert 2147483648 == Long.valueOf('2147483648') // Long type used, value too large for an Integer
assert 456G == new BigInteger('456')
assert 456g == new BigInteger('456')
assert 123.45 == new BigDecimal('123.45') // default BigDecimal type used
assert .321 == new BigDecimal('.321')
assert 1.200065D == Double.valueOf('1.200065')
assert 1.234F == Float.valueOf('1.234')
assert 1.23E23D == Double.valueOf('1.23E23')
assert 0b1111L.class == Long // binary
assert 0xFFi.class == Integer // hexadecimal
assert 034G.class == BigInteger // octal


// base and exponent are ints and the result can be represented by an Integer
assert 2**3 instanceof Integer    //  8
assert 10**9 instanceof Integer    //  1_000_000_000

// the base is a long, so fit the result in a Long
// (although it could have fit in an Integer)
assert 5L**2 instanceof Long       //  25

// the result can't be represented as an Integer or Long, so return a BigInteger
assert 100**10 instanceof BigInteger //  10e20
assert 1234**123 instanceof BigInteger //  170515806212727042875...

// the base is a BigDecimal and the exponent a negative int
// but the result can be represented as an Integer
assert 0.5**-2 instanceof Integer    //  4

// the base is an int, and the exponent a negative float
// but again, the result can be represented as an Integer
assert 1**-0.3f instanceof Integer    //  1

// the base is an int, and the exponent a negative int
// but the result will be calculated as a Double
// (both base and exponent are actually converted to doubles)
assert 10**-1 instanceof Double     //  0.1

// the base is a BigDecimal, and the exponent is an int, so return a BigDecimal
assert 1.2**10 instanceof BigDecimal //  6.1917364224

// the base is a float or double, and the exponent is an int
// but the result can only be represented as a Double value
assert 3.4f**5 instanceof Double     //  454.35430372146965
assert 5.6d**2 instanceof Double     //  31.359999999999996

// the exponent is a decimal value
// and the result can only be represented as a Double value
assert 7.8**1.9 instanceof Double     //  49.542708423868476
assert 2**0.1f instanceof Double     //  1.0717734636432956

//Booleans

def myBooleanVariable = true
boolean untypedBooleanVar = false
booleanField = true