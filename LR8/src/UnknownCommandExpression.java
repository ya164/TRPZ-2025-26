public class UnknownCommandExpression implements AbstractExpression {
    private final String originalLine;

    public UnknownCommandExpression(String originalLine) {
        this.originalLine = originalLine;
    }

    @Override
    public void interpret(CommandContext context) {
        System.out.println("Невідома команда: " + originalLine);
    }
}
