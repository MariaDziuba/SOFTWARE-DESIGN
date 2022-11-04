package tokenizer.tokens

import vizitors.TokenVisitor

class NumberToken(val `val`: Int) : Token {

    override fun accept(visitor: TokenVisitor?) {
        visitor?.visit(this)
    }
}