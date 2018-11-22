package prop.assignment0;

import java.io.IOException;

public class Parser implements IParser {
    public ITokenizer tokenizer;

    public Parser() {
        tokenizer = new Tokenizer();
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        tokenizer.open(fileName);
        tokenizer.moveNext();
    }

    @Override
    public INode parse() throws IOException, TokenizerException, ParserException {
        Lexeme current = tokenizer.current();
        if (current.token() == Token.IDENT) {
            return parseAssignNode();
        }
        throw new ParserException("Unexpected token: " + current.value());
    }

    private FactorNode parseFactorNode() throws ParserException, IOException, TokenizerException {
        System.out.println("Begin parse factor");

        Lexeme current = tokenizer.current();
        FactorNode node = null;

        if (current.token() == Token.INT_LIT) {
            node = new FactorNode(current);
            tokenizer.moveNext();
        } else if (current.token() == Token.LEFT_PAREN) {
            Lexeme leftParen = current;
            tokenizer.moveNext();
            ExpressionNode expr = parseExpressionNode();

            current = tokenizer.current();
            if (current.token() != Token.RIGHT_PAREN) {
                throw new ParserException("Missing paranthesis");
            }

            Lexeme rightParen = current;
            node = new FactorNode(leftParen, expr, rightParen);
            tokenizer.moveNext();
        } else {
            throw new ParserException("Unexpected token parsing FactorNode " + current.toString());
        }
        return node;
    }

    private ExpressionNode parseExpressionNode() throws ParserException, IOException, TokenizerException {
        System.out.println("Begin parse expression");

        TermNode term = parseTermNode();
        Lexeme current = tokenizer.current();

        if (current.token() == Token.ADD_OP || current.token() == Token.SUB_OP) {
            Lexeme op = current;
            tokenizer.moveNext();
            ExpressionNode expr = parseExpressionNode();
            return new ExpressionNode(term, op, expr);
        }
        return new ExpressionNode(term);
    }

    private TermNode parseTermNode() throws ParserException, IOException, TokenizerException {
        FactorNode factor = parseFactorNode();
        Lexeme current = tokenizer.current();

        if (current.token() == Token.DIV_OP || current.token() == Token.MULT_OP) {
            Lexeme op = current;
            tokenizer.moveNext();
            TermNode term = parseTermNode();
            return new TermNode(factor, op, term);
        }
        return new TermNode(factor);
    }

    private AssignmentNode parseAssignNode() throws ParserException, IOException, TokenizerException {
        if (tokenizer.current().token() != Token.IDENT) {
            throw new ParserException("Unexpected token parsing AssignNode: " + tokenizer.current().toString());
        }

        Lexeme id = tokenizer.current();
        tokenizer.moveNext();

        if (tokenizer.current().token() != Token.ASSIGN_OP) {
            throw new ParserException("Unexpected token parsing AssignNode: " + tokenizer.current().toString());
        }

        Lexeme op = tokenizer.current();
        tokenizer.moveNext();
        ExpressionNode expr = parseExpressionNode();

        if (tokenizer.current().token() != Token.SEMICOLON) {
            throw new ParserException("Unexpected token parsing AssignNode: " + tokenizer.current().toString());
        }

        Lexeme semiColon = tokenizer.current();
        tokenizer.moveNext();

        return new AssignmentNode(id, op, expr, semiColon);
    }

    @Override
    public void close() throws IOException {
        tokenizer.close();
    }
}