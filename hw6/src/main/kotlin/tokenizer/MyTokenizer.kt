package tokenizer

import tokenizer.state.End
import tokenizer.state.Start
import tokenizer.state.State
import tokenizer.tokens.Token
import java.util.ArrayList

class MyTokenizer(input: String) {
    private var state: State
    fun tokenize(): MutableList<Token> {
        val tokens: MutableList<Token> = ArrayList()
        while (state !is End) {
            state = state.nextState(tokens)
        }
        return tokens
    }

    init {
        state = Start(input, 0)
    }
}