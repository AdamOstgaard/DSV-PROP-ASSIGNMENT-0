package prop.assignment0;

public class TermNode implements INode {
    private FactorNode factor;
    private TermNode term;
    private Lexeme operator;

    public TermNode(FactorNode factor) {
        this.factor = factor;
    }

    public TermNode(FactorNode factor, Lexeme operator, TermNode term) {
        this(factor);
        this.operator = operator;
        this.term = term;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        if (operator != null) {
            if (operator.token() == Token.MULT_OP) {
                return (double) factor.evaluate(null) * (double) term.evaluate(null);
            }

            if (operator.token() == Token.DIV_OP) {
                return (double) factor.evaluate(null) / (double) term.evaluate(null);
            }
        }
        return factor.evaluate(null);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        writeTabbed(builder, tabs, "TermNode");
        factor.buildString(builder, tabs + 1);

        if (operator != null) {
            writeTabbed(builder, tabs + 1, operator.toString());
            term.buildString(builder, tabs + 1);
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
