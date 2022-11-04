package visitors

import tokenizer.tokens.Brace
import tokenizer.tokens.NumberToken
import tokenizer.tokens.Operation
import vizitors.TokenVisitor
import java.util.Stack

class CalcVisitor : TokenVisitor {
    private val stack: Stack<Double> = Stack()
    fun finishCalc(): Double {
        if (stack.size != 1) {
            throw RuntimeException("Incorrect expression in RPN: too many arguments in stack")
        }
        return stack.pop()
    }

    override fun visit(token: NumberToken) {
        stack.push(token.`val`.toDouble())
    }

    override fun visit(token: Brace) {
        throw RuntimeException("Unexpected token BRACE")
    }

    override fun visit(token: Operation) {
        if (stack.size < 2) {
            throw RuntimeException("Incorrect expression in RPN: not enough arguments in stack")
        }
        val b: Double = stack.pop()
        val a: Double = stack.pop()
        if (token.isPlus) stack.add(a + b)
        if (token.isMinus) stack.add(a - b)
        if (token.isMul) stack.add(a * b)
        if (token.isDiv) stack.add(a / b)
    }
}