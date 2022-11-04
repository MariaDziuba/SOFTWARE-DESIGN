package tokenizer.tokens

import exceptions.TokenizerException
import vizitors.TokenVisitor

class Operation(c: Char) : Token {
    private var type: OperationType? = null
    val isPlus: Boolean
        get() = type == OperationType.PLUS
    val isMinus: Boolean
        get() = type == OperationType.MINUS
    val isMul: Boolean
        get() = type == OperationType.MUL
    val isDiv: Boolean
        get() = type == OperationType.DIV
    val priority: Int
        get() = if (isPlus || isMinus) {
            1
        } else 2

    init {
        when (c) {
            '+' -> type = OperationType.PLUS
            '-' -> type = OperationType.MINUS
            '*' -> type = OperationType.MUL
            '/' -> type = OperationType.DIV
            else -> throw TokenizerException("Incorrect Operation char '$c'")
        }
    }

    override fun accept(visitor: TokenVisitor?) {
        visitor?.visit(this)
    }
}

internal enum class OperationType {
    PLUS, MINUS, MUL, DIV
}