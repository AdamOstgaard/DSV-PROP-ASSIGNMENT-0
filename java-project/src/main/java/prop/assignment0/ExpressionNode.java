package prop.assignment0;

public class ExpressionNode implements INode {
    private TermNode term;
    private Lexeme operator;
    private ExpressionNode expression;

    public ExpressionNode(TermNode term) {
        this.term = term;
    }

    public ExpressionNode(TermNode term, Lexeme operator, ExpressionNode expression) {
        this(term);
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        writeTabbed(builder, tabs, "ExpressionNode");
        term.buildString(builder, tabs + 1);
        if (operator != null) {
            writeTabbed(builder, tabs, operator.toString());
            expression.buildString(builder, tabs + 1);
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
