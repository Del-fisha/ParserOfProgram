import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<File> filesToParse;
    private static final Scanner scanner;

    static {
        filesToParse = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        System.out.println("Введите стартовый путь:");
        File startingDirectory = getStartDir();
        System.out.println("Введите путь сохранения:");
        File finishedDirectory = getFinishDir();
        System.out.println("Введите типы файлов через пробел:");
        String[] types = scanner.nextLine().split(" ");
        parseFiles(types, startingDirectory);

        File outputFile = new File(finishedDirectory, "ParsedProgram.txt");

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)))) {
            for (File file : filesToParse) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String nameOfFile = file.getName();
                    writer.println(nameOfFile + ":");

                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.println(line);
                    }
                    writer.println();
                } catch (IOException exception) {
                    System.out.println("Ошибка при чтении/записи файла: " + file.getName() + " " + exception.getMessage());
                }
            }
            System.out.println("Файлы успешно обработаны и сохранены в: " + outputFile.getAbsolutePath());

        } catch (IOException exception) {
            System.out.println("Ошибка при создании/записи в файл: " + outputFile.getName() + " " + exception.getMessage());
        }
    }

    private static void parseFiles(String[] types, File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    for (String type : types) {
                        if (file.getName().endsWith(type)) {
                            filesToParse.add(file);
                        }
                    }
                } else if (file.isDirectory()) {
                    parseFiles(types, file);
                }
            }
        }
    }

    private static File getStartDir() {
        String startDir = scanner.nextLine();
        File startDirFile = new File(startDir);
        while (!startDirFile.exists() || !startDirFile.isDirectory()) {
            System.out.println("Директория не найдена\nВведите стартовый путь:");
            startDir = scanner.nextLine();
            startDirFile = new File(startDir);
        }

        return startDirFile;
    }

    private static File getFinishDir() {
        String finishDirPath = scanner.nextLine();
        File finishDir = new File(finishDirPath);
        while (!finishDir.exists() || !finishDir.isDirectory()) {
            System.out.println("Директория не найдена\nВведите путь сохранения:");
            finishDirPath = scanner.nextLine();
            finishDir = new File(finishDirPath);
        }

        return finishDir;
    }
}
