public class MoveCommandExpression implements AbstractExpression {
    private final String src;
    private final String dst;

    public MoveCommandExpression(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    @Override
    public void interpret(CommandContext context) {
        context.move(src, dst);
    }
}
