package vizitors

import tokenizer.tokens.Brace
import tokenizer.tokens.NumberToken
import tokenizer.tokens.Operation

class PrintVisitor : TokenVisitor {
    override fun visit(token: NumberToken) {
        print("NUMBER(" + token.`val`.toString() + ") ")
    }

    override fun visit(token: Brace) {
        if (token.isOpen) {
            print("OPEN ")
        } else if (token.isClosed) {
            print("CLOSED ")
        }
    }

    override fun visit(token: Operation) {
        when {
            token.isPlus -> {
                print("PLUS ")
            }
            token.isMinus -> {
                print("MINUS ")
            }
            token.isMul -> {
                print("MUL ")
            }
            token.isDiv -> {
                print("DIV ")
            }
        }
    }
}