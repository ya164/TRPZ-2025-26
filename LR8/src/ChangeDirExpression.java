public class ChangeDirExpression implements AbstractExpression {
    private final String path;

    public ChangeDirExpression(String path) {
        this.path = path;
    }

    @Override
    public void interpret(CommandContext context) {
        context.changeDirectory(path);
    }
}
