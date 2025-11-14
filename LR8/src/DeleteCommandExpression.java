public class DeleteCommandExpression implements AbstractExpression {
    private final String path;

    public DeleteCommandExpression(String path) {
        this.path = path;
    }

    @Override
    public void interpret(CommandContext context) {
        context.delete(path);
    }
}
