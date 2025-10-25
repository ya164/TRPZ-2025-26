public class Shell {

    public static void main(String[] args) {
        try {
            FileManager manager = new FileManager();

            TextReport reportTemplate = new TextReport(
                    "ReportTemplate.txt",
                    "/templates",
                    "Admin",
                    "Щоденний звіт про роботу системи..."
            );
            manager.registerTemplate("textReport", reportTemplate);

            Directory projectTemplate = new Directory("ProjectTemplate", "/templates");

            Directory srcDir = new Directory("src", "/templates/ProjectTemplate/src");
            srcDir = srcDir.add(new File("Main.java", "/templates/ProjectTemplate/src", 120));
            srcDir = srcDir.add(new File("Utils.java", "/templates/ProjectTemplate/src", 80));

            Directory docsDir = new Directory("docs", "/templates/ProjectTemplate/docs");
            docsDir = docsDir.add(new TextReport(
                    "ProjectReport.txt",
                    "/templates/ProjectTemplate/docs",
                    "TeamLead",
                    "Опис функціональності проєкту..."
            ));

            projectTemplate = projectTemplate.add(srcDir);
            projectTemplate = projectTemplate.add(docsDir);

            manager.registerTemplate("projectDir", projectTemplate);

            FileSystemObject dailyReport = manager.createFromTemplate(
                    "textReport",
                    "/reports",
                    "DailyReport_25_10.txt"
            );

            FileSystemObject myProject = manager.createFromTemplate(
                    "projectDir",
                    "/projects",
                    "MyAwesomeApp"
            );

            System.out.println("=== Демонстрація патерну Prototype ===\n");

            System.out.println("1. Оригінальний шаблон звіту:");
            reportTemplate.display(0);

            System.out.println("\n2. Новий звіт, створений шляхом клонування:");
            dailyReport.display(0);

            System.out.println("\n3. Новий проєкт, створений шляхом глибокого клонування:");
            myProject.display(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}