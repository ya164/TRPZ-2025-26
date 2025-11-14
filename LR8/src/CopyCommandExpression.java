public class CopyCommandExpression implements AbstractExpression {
    private final String src;
    private final String dst;

    public CopyCommandExpression(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    @Override
    public void interpret(CommandContext context) {
        context.copy(src, dst);
    }
}
