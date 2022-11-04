package tokenizer.tokens

import vizitors.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor?)
}