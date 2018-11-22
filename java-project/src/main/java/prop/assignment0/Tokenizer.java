package prop.assignment0;

import java.io.IOException;

public class Tokenizer implements ITokenizer {
    private Scanner scanner;
    private Lexeme currentLexeme;
    private Token currentToken;
    private TokenizerState state;

    public Tokenizer() {
        scanner = new Scanner();
        currentLexeme = new Lexeme(null, Token.NULL);
        currentToken = Token.NULL;
        state = TokenizerState.idle;
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);
        scanner.moveNext();
    }

    @Override
    public Lexeme current() {
        return currentLexeme;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        String value = "";
        currentToken = Token.NULL;

        while (state != TokenizerState.done) {
            char c = scanner.current();

            if (c == Scanner.EOF) {
                currentToken = Token.EOF;
                state = TokenizerState.done;
                return;
            }

            if (Character.isWhitespace(c)) {
                if (state != TokenizerState.idle) {
                    state = TokenizerState.done;
                } else {
                    scanner.moveNext();
                }
                continue;
            }

            if (Character.isAlphabetic(c)) {
                if (state != TokenizerState.idle && state != TokenizerState.readingIdentity) {
                    throw new TokenizerException("Unexpected character: " + c);
                }
                state = TokenizerState.readingIdentity;
                currentToken = Token.IDENT;

                value += c;
                scanner.moveNext();

                continue;
            }

            if (Character.isDigit(c)) {
                if (state != TokenizerState.idle && state != TokenizerState.readingInt) {
                    throw new TokenizerException("Unexpected character: " + c);
                }
                state = TokenizerState.readingInt;
                currentToken = Token.INT_LIT;
                value += c;
                scanner.moveNext();
                continue;
            }

            if (state != TokenizerState.idle) {
                state = TokenizerState.done;
                continue;
            }

            currentToken = getOpToken(c);

            if (currentToken == null) {
                throw new TokenizerException("Unexpected character: " + c);
            }

            value += c;

            scanner.moveNext();

            state = TokenizerState.done;
        }
        state = TokenizerState.idle;

        try {
            double valNum = Double.parseDouble(value);
            this.currentLexeme = new Lexeme(valNum, currentToken);
        } catch (NumberFormatException e) {
            this.currentLexeme = new Lexeme(value, currentToken);
        }
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }

    private Token getOpToken(char c) {
        switch (c) {
        case '+':
            return Token.ADD_OP;
        case '-':
            return Token.SUB_OP;
        case '*':
            return Token.MULT_OP;
        case '/':
            return Token.DIV_OP;
        case '=':
            return Token.ASSIGN_OP;
        case '(':
            return Token.LEFT_PAREN;
        case ')':
            return Token.RIGHT_PAREN;
        case ';':
            return Token.SEMICOLON;
        default:
            return null;
        }
    }
}