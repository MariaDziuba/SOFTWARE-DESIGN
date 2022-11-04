package tokenizer.state

import tokenizer.tokens.NumberToken
import tokenizer.tokens.Token

class Number : State {
    private var value: Int

    constructor(input: String, pos: Int) : super(input, pos) {
        value = 0
    }

    private constructor(input: String, pos: Int, `val`: Int) : super(input, pos) {
        this.value = `val`
    }

    override fun nextState(tokens: MutableList<Token>): State {
        val checked: State? = checkSpaceAndEnd()
        if (checked != null) {
            tokens.add(NumberToken(value))
            return checked
        }
        val c: Char = input[pos]
        if (!Character.isDigit(c)) {
            tokens.add(NumberToken(value))
            return Start(input, pos)
        }
        value = value * 10 + Character.getNumericValue(c)
        return Number(input, pos + 1, value)
    }
}