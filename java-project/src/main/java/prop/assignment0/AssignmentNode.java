package prop.assignment0;

public class AssignmentNode implements INode {
    private Lexeme id;
    private Lexeme operation;
    private Lexeme semiColon;
    private ExpressionNode expression;

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return null;
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {

    }

}