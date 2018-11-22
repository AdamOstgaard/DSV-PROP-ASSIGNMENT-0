package prop.assignment0;

public class ExpressionNode implements INode {
    private TermNode term;
    private Lexeme operator;
    private ExpressionNode expression;

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {

    }

}
