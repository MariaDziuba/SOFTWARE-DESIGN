package vizitors

import exceptions.ParserException
import tokenizer.tokens.Brace
import tokenizer.tokens.NumberToken
import tokenizer.tokens.Operation
import tokenizer.tokens.Token
import java.util.*
import kotlin.collections.ArrayList

class ParserVisitor : TokenVisitor {
    private val tokensInRPN: MutableList<Token> = ArrayList()
    private val stack: Stack<Token> = Stack()
    fun finishParsing(): MutableList<Token> {
        while (!stack.empty()) {
            tokensInRPN.add(stack.pop())
        }
        return tokensInRPN
    }

    fun reset() {
        tokensInRPN.clear()
        stack.clear()
    }

    override fun visit(token: NumberToken) {
        tokensInRPN.add(token)
    }

    override fun visit(token: Brace) {
        if (token.isOpen) {
            stack.push(token)
            return
        }
        assert(token.isClosed)
        while (!stack.empty()) {
            val top: Token = stack.peek()
            if (top is Brace) {
                assert((top).isOpen)
                stack.pop()
                return
            }
            tokensInRPN.add(stack.pop())
        }
        throw ParserException("Incorrect expression")
    }

    override fun visit(token: Operation) {
        while (!stack.empty()) {
            val top: Token = stack.peek()
            if (top !is Operation || (top).priority < token.priority) {
                break
            }
            tokensInRPN.add(stack.pop())
        }
        stack.push(token)
    }
}