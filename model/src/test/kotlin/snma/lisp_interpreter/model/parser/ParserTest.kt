//package snma.lisp_interpreter.model.parser
//
//import io.kotest.core.spec.style.FunSpec
//import io.kotest.matchers.shouldBe
//import snma.lisp_interpreter.model.lexer.Token
//import snma.lisp_interpreter.model.lexer.TokenType
//
//private data class ParserTestCase(
//    val name: String,
//    val tokens: List<Token>,
//    val treeExpected: Node
//)
//
//class ParserTest: FunSpec({
//    sequenceOf(
//        ParserTestCase(
//            name = "(+ 1 2)",
//            tokens = listOf(
//                Token(TokenType.PARENTHESES_OPEN, "("),
//                Token(TokenType.LITERAL, "+"),
//                Token(TokenType.LITERAL, "1"),
//                Token(TokenType.LITERAL, "2"),
//                Token(TokenType.PARENTHESES_CLOSE, ")"),
//            ),
//            treeExpected = Branch(listOf(
//                Leaf("+"),
//                Leaf("1"),
//                Leaf("2"),
//            )),
//        ),
//        ParserTestCase(
//            name = "(+ 10 (* 20 30))",
//            tokens = listOf(
//                Token(TokenType.PARENTHESES_OPEN, "("),
//                Token(TokenType.LITERAL, "+"),
//                Token(TokenType.LITERAL, "10"),
//                Token(TokenType.PARENTHESES_OPEN, "("),
//                Token(TokenType.LITERAL, "*"),
//                Token(TokenType.LITERAL, "20"),
//                Token(TokenType.LITERAL, "30"),
//                Token(TokenType.PARENTHESES_CLOSE, ")"),
//                Token(TokenType.PARENTHESES_CLOSE, ")"),
//            ),
//            treeExpected = Branch(listOf(
//                Leaf("+"),
//                Leaf("10"),
//                Branch(listOf(
//                    Leaf("*"),
//                    Leaf("20"),
//                    Leaf("30"),
//                )),
//            )),
//        ),
//        ParserTestCase(
//            name = "((+ 1 2) (+ 30 400))",
//            tokens = listOf(
//                Token(TokenType.PARENTHESES_OPEN, "("),
//                Token(TokenType.PARENTHESES_OPEN, "("),
//                Token(TokenType.LITERAL, "+"),
//                Token(TokenType.LITERAL, "1"),
//                Token(TokenType.LITERAL, "2"),
//                Token(TokenType.PARENTHESES_CLOSE, ")"),
//                Token(TokenType.PARENTHESES_OPEN, "("),
//                Token(TokenType.LITERAL, "+"),
//                Token(TokenType.LITERAL, "30"),
//                Token(TokenType.LITERAL, "400"),
//                Token(TokenType.PARENTHESES_CLOSE, ")"),
//                Token(TokenType.PARENTHESES_CLOSE, ")"),
//            ),
//            treeExpected = Branch(listOf(
//                Branch(listOf(
//                    Leaf("+"),
//                    Leaf("1"),
//                    Leaf("2"),
//                )),
//                Branch(listOf(
//                    Leaf("+"),
//                    Leaf("30"),
//                    Leaf("400"),
//                )),
//            )),
//        ),
//    ).forEach { (name, tokens, treeExpected) ->
//        test(name) {
//            Parser().parse(tokens) shouldBe treeExpected
//        }
//    }
//})