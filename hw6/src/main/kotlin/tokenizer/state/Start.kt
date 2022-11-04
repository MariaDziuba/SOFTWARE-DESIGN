package tokenizer.state

import exceptions.TokenizerException
import tokenizer.tokens.Brace
import tokenizer.tokens.Operation
import tokenizer.tokens.Token

class Start(input: String, pos: Int) : State(input, pos) {
    override fun nextState(tokens: MutableList<Token>): State {
        val checked: State? = checkSpaceAndEnd()
        if (checked != null) {
            return checked
        }
        val c: Char = input[pos]
        if (Character.isDigit(c)) {
            return Number(input, pos)
        }
        val token: Token
        if (c == '(' || c == ')') {
            token = Brace(c)
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            token = Operation(c)
        } else {
            throw TokenizerException("Incorrect char \'$c\' at Start.nextState()")
        }
        tokens.add(token)
        pos++
        return this
    }
}