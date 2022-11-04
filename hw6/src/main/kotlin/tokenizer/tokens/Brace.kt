package tokenizer.tokens

import exceptions.TokenizerException
import vizitors.TokenVisitor

class Brace(c: Char) : Token {
    private var type: BraceType? = null
    val isOpen: Boolean
        get() = type == BraceType.OPEN
    val isClosed: Boolean
        get() = type == BraceType.CLOSED

    init {
        if (c == '(') {
            type = BraceType.OPEN
        } else if (c == ')') {
            type = BraceType.CLOSED
        } else {
            throw TokenizerException("Incorrect brace char \'$c\'")
        }
    }

    override fun accept(visitor: TokenVisitor?) {
        visitor?.visit(this)
    }
}

internal enum class BraceType {
    OPEN, CLOSED
}