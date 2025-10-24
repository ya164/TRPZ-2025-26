public class Shell {
    public static void main(String[] args) {
        try {
            FileManager manager = new FileManager();

            System.out.println("--- Реєстрація шаблонів ---");

            File docTemplate = new File("template.docx", "", 1024);
            manager.registerTemplate("document", docTemplate);
            System.out.println("Шаблон 'document' зареєстровано.");

            Directory projectTemplate = new Directory("NewProject", "");
            projectTemplate.add(new Directory("src", ""));
            projectTemplate.add(new Directory("docs", ""));
            projectTemplate.add(new File("README.md", "", 512));
            manager.registerTemplate("project", projectTemplate);
            System.out.println("Шаблон 'project' зареєстровано.\n");

            System.out.println("--- Створення об'єктів з шаблонів ---");

            File financialReport = (File) manager.createFromTemplate(
                    "document", "C:/Reports/", "FinancialReport_Q3.docx");

            Directory myApp = (Directory) manager.createFromTemplate(
                    "project", "C:/Development/", "MyAwesomeApp");

            System.out.println("Створено новий документ:");
            financialReport.display(0);

            System.out.println("\nСтворено новий проект:");
            myApp.display(0);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}