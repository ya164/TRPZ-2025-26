import java.nio.file.Paths;
import java.util.Scanner;

public class ShellInterpreter {

    public static void main(String[] args) {
        CommandContext context = new CommandContext(Paths.get("."));
        System.out.println("Підтримувані команди: ls, cd, copy, move, rm, mkdir, exit");
        System.out.println("Поточна директорія: " + context.getCurrentDirectory());

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print(context.getCurrentDirectory() + " > ");
                String line = scanner.nextLine();

                if (line == null) break;

                String trimmed = line.trim();
                if (trimmed.equalsIgnoreCase("exit") || trimmed.equalsIgnoreCase("quit")) {
                    System.out.println("Вихід.");
                    break;
                }

                AbstractExpression expression = CommandParser.parse(trimmed);
                expression.interpret(context);
            }
        }
    }
}
