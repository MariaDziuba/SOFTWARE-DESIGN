package tokenizer.state

import tokenizer.tokens.Token

abstract class State(var input: String, var pos: Int) {
    abstract fun nextState(tokens: MutableList<Token>): State
    fun checkSpaceAndEnd(): State? {
        if (pos >= input.length) {
            return End(input, pos)
        }
        return if (Character.isWhitespace(input[pos])) {
            Space(input, pos)
        } else null
    }
}