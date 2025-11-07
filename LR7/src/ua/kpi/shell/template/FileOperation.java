package ua.kpi.shell.template;

import java.nio.file.Path;

public abstract class FileOperation {
    protected final Path source;
    protected final Path target;

    protected FileOperation(Path source, Path target) {
        this.source = source;
        this.target = target;
    }

    public final void execute() {
        long started = System.currentTimeMillis();
        System.out.println("=== " + getName() + " ===");
        try {
            before();
            checkPreconditions();
            performOperation();
            verify();
            after();
            System.out.println(getName() + " -> Виконано (" +
                    (System.currentTimeMillis() - started) + " ms)");
        } catch (Exception e) {
            onError(e);
        }
        System.out.println();
    }

    protected abstract String getName();
    protected abstract void checkPreconditions() throws Exception;
    protected abstract void performOperation() throws Exception;
    protected abstract void verify() throws Exception;

    protected void before() throws Exception {}
    protected void after() throws Exception {}
    protected void onError(Exception e) {
        System.err.println(getName() + " -> Помилка: " + e.getMessage());
    }
}


