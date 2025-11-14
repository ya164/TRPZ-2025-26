public class MkdirCommandExpression implements AbstractExpression {
    private final String path;

    public MkdirCommandExpression(String path) {
        this.path = path;
    }

    @Override
    public void interpret(CommandContext context) {
        context.createDirectory(path);
    }
}
