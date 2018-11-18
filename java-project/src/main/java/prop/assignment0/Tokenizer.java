package prop.assignment0;

import java.io.IOException;

public class Tokenizer implements ITokenizer {
    private Scanner scanner;
    private Lexeme currentLexeme;
    private Token currentToken;

    public Tokenizer() {
        scanner = new Scanner();
        currentLexeme = new Lexeme(null, Token.NULL);
        currentToken = Token.NULL;
    }

    @Override
    public void open(String fileName) throws IOException, TokenizerException {
        scanner.open(fileName);
    }

    @Override
    public Lexeme current() {
        return null;
    }

    @Override
    public void moveNext() throws IOException, TokenizerException {
        if (scanner.current() != scanner.EOF) {
            String value = "";
            do {
                scanner.moveNext();
                char c = scanner.current();
                if (isIdentity(c)) {
                    currentToken = Token.IDENT;
                    value += c;
                    continue;
                }

                if (Character.isDigit(c)) {
                    currentToken = Token.INT_LIT;
                    value += c;
                    continue;
                }

                if (currentToken == Token.INT_LIT || currentToken == Token.IDENT) {
                    break;
                }

                if (c == ' ') {
                    scanner.moveNext();
                    c = scanner.current();
                }

                if (c == '+') {
                    currentToken = Token.ADD_OP;
                }

                if (c == '-') {
                    currentToken = Token.SUB_OP;
                }
            } while (currentToken != Token.NULL);
            this.currentLexeme = new Lexeme(value, currentToken);
        }
    }

    @Override
    public void close() throws IOException {

    }

    private boolean isIdentity(char c) {
        return Character.isAlphabetic(c);
    }
}