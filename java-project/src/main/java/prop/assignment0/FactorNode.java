package prop.assignment0;

public class FactorNode implements INode {
    private Lexeme leftParen;
    private Lexeme rightParen;
    private Lexeme value;
    private ExpressionNode expression;

    public FactorNode(Lexeme value) {
        this.value = value;
    }

    public FactorNode(Lexeme leftParen, ExpressionNode expression, Lexeme rightParen) {
        this.expression = expression;
        this.leftParen = leftParen;
        this.rightParen = rightParen;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (expression != null) {
            return expression.evaluate(null);
        }
        return (double) value.value();
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        writeTabbed(builder, tabs, "FactorNode");
        if (expression != null) {
            writeTabbed(builder, tabs + 1, leftParen.toString());
            expression.buildString(builder, tabs + 1);
            writeTabbed(builder, tabs + 1, rightParen.toString());
        } else {
            writeTabbed(builder, tabs + 1, value.toString());
        }
    }

    private void writeTabbed(StringBuilder builder, int tabs, String text) {
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(text);
        builder.append("\n");
    }
}
