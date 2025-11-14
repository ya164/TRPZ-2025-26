public class CommandParser {

    public static AbstractExpression parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            return ctx -> {};
        }

        String trimmed = line.trim();
        String[] parts = trimmed.split("\\s+");

        String cmd = parts[0].toLowerCase();

        switch (cmd) {
            case "ls":
                return new ListCommandExpression();

            case "cd":
                if (parts.length >= 2) {
                    return new ChangeDirExpression(parts[1]);
                } else {
                    System.out.println("Використання: cd <шлях>");
                    return ctx -> {};
                }

            case "copy":
                if (parts.length >= 3) {
                    return new CopyCommandExpression(parts[1], parts[2]);
                } else {
                    System.out.println("Використання: copy <джерело> <призначення>");
                    return ctx -> {};
                }

            case "move":
                if (parts.length >= 3) {
                    return new MoveCommandExpression(parts[1], parts[2]);
                } else {
                    System.out.println("Використання: move <джерело> <призначення>");
                    return ctx -> {};
                }

            case "rm":
                if (parts.length >= 2) {
                    return new DeleteCommandExpression(parts[1]);
                } else {
                    System.out.println("Використання: rm <шлях>");
                    return ctx -> {};
                }

            case "mkdir":
                if (parts.length >= 2) {
                    return new MkdirCommandExpression(parts[1]);
                } else {
                    System.out.println("Використання: mkdir <назва_каталогу>");
                    return ctx -> {};
                }

            case "exit":
            case "quit":
                return ctx -> {};

            default:
                return new UnknownCommandExpression(line);
        }
    }
}
