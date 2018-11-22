package prop.assignment0;

public class AssignmentNode implements INode {
    private Lexeme id;
    private Lexeme operation;
    private Lexeme semiColon;
    private ExpressionNode expression;

    public AssignmentNode(Lexeme id, Lexeme operation, ExpressionNode expression, Lexeme semiColom) {
        this.id = id;
        this.operation = operation;
        this.semiColon = semiColom;
        this.expression = expression;
    }

    @Override
    public Object evaluate(Object[] args) throws Exception {
        return id.value().toString() + operation.value().toString() + expression.evaluate(null);
    }

    @Override
    public void buildString(StringBuilder builder, int tabs) {
        writeTabbed(builder, tabs, "AssignmentNode");
        writeTabbed(builder, tabs + 1, id.toString());
        writeTabbed(builder, tabs + 1, operation.toString());
        expression.buildString(builder, tabs + 1);
        writeTabbed(builder, tabs + 1, semiColon.toString());
    }

    private void writeTabbed(StringBuilder builder, int tabs, String text) {
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(text);
        builder.append("\n");
    }
}