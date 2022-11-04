import visitors.CalcVisitor
import exceptions.CalcException
import exceptions.ParserException
import vizitors.ParserVisitor
import vizitors.PrintVisitor
import tokenizer.MyTokenizer
import tokenizer.tokens.Token

fun main() {
    val input = readLine()!!
    println(input)
    try {
        val t = MyTokenizer(input)
        var tokens: MutableList<Token> = t.tokenize()
        val parser = ParserVisitor()
        for (token in tokens) {
            token.accept(parser)
        }
        tokens = parser.finishParsing()
        val printVisitor = PrintVisitor()
        for (token in tokens) {
            token.accept(printVisitor)
        }
        val calc = CalcVisitor()
        for (token in tokens) {
            token.accept(calc)
        }
        println(
            """

                    Result is ${calc.finishCalc()}
                    """.trimIndent()
        )
    } catch (e: CalcException) {
        System.err.println(
            """

                    Calculating error: ${e.message}
                    """.trimIndent()
        )
    } catch (e: ParserException) {
        System.err.println(
            """

                    Parsing error: ${e.message}
                    """.trimIndent()
        )
    }
}