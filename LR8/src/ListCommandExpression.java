public class ListCommandExpression implements AbstractExpression {
    @Override
    public void interpret(CommandContext context) {
        context.listDirectory();
    }
}
