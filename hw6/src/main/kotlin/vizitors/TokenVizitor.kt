package vizitors

import tokenizer.tokens.Brace
import tokenizer.tokens.NumberToken
import tokenizer.tokens.Operation

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: Brace)
    fun visit(token: Operation)
}