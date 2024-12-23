package com.alejandro.patterns

class TwoupMessages {
    def welcome = 'Welcome to the twoup game, you start with $1000'
    def done = 'Sorry, you have no money left, goodbye'
}

class TwoupInputConverter {
    def convert(input) { input.toInteger() }
}

class TwoupControl {
    private money = 1000
    private random = new Random()

    private tossWasHead() {
        def next = random.nextInt()
        return next % 2 == 0
    }

    def moreTurns() {
        if (money > 0) {
            println "You have $money, how much would you like to bet?"
            return true
        }

        false
    }

    def play(amount) {
        def coin1 = tossWasHead()
        def coin2 = tossWasHead()
        if (coin1 && coin2) {
            money += amount
            println 'You win'
        } else if (!coin1 && !coin2) {
            money -= amount
            println 'You lose'
        } else {
            println 'Draw'
        }
    }
}

//Now, let’s look at the game-specific code for a number guessing game:

class GuessGameMessages {
    def welcome = 'Welcome to the guessing game, my secret number is between 1 and 100'
    def done = 'Correct'
}

class GuessGameInputConverter {
    def convert(input) { input.toInteger() }
}

class GuessGameControl {
    private lower = 1
    private upper = 100
    private guess = new Random().nextInt(upper - lower) + lower

    def moreTurns() {
        def done = (lower == guess || upper == guess)
        if (!done) {
            println "Enter a number between $lower and $upper"
        }

        !done
    }

    def play(nextGuess) {
        if (nextGuess <= guess) {
            lower = [lower, nextGuess].max()
        }
        if (nextGuess >= guess) {
            upper = [upper, nextGuess].min()
        }
    }
}

//Now, let’s write our factory code:
def guessFactory = [messages: GuessGameMessages, control: GuessGameControl, converter: GuessGameInputConverter]
def twoupFactory = [messages: TwoupMessages, control: TwoupControl, converter: TwoupInputConverter]

class GameFactory {
    def static factory

    def static getMessages() { return factory.messages.newInstance() }

    def static getControl() { return factory.control.newInstance() }

    def static getConverter() { return factory.converter.newInstance() }
}

//The important aspect of this factory is that it allows selection of an entire family of concrete classes.

//        Here is how we would use the factory:

GameFactory.factory = twoupFactory
def messages = GameFactory.messages
def control = GameFactory.control
def converter = GameFactory.converter

println messages.welcome
def reader = new BufferedReader(new InputStreamReader(System.in))
while (control.moreTurns()) {
    def input = reader.readLine().trim()
    control.play(converter.convert(input))
}