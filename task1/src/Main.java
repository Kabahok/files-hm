import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static String curDir = System.getProperty("user.dir");
    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        List<String> dirInGames = Arrays.asList("src", "res", "savegames", "temp");
        List<String> dirInSrc = Arrays.asList("main", "test");
        List<String> filesInMain = Arrays.asList("Main.java", "Utils.java");
        List<String> dirInRes = Arrays.asList("drawables", "vectors", "icons");

        createDirs(dirInGames, String.format("%s/src/Games", curDir));
        createDirs(dirInSrc, String.format("%s/src/Games/src", curDir));
        createDirs(dirInRes, String.format("%s/src/Games/res", curDir));
        createFiles(filesInMain, String.format("%s/src/Games/src/main", curDir));
        createFiles(Arrays.asList("temp.txt"), String.format("%s/src/Games/temp", curDir));

        writerLogs(String.format("%s/src/Games/temp/temp.txt", curDir), sb.toString());
    }

    public static String createDirLog(String nameDir, boolean flag) {
        return flag ? String.format("Папка %s была создана успешно\n", nameDir) : String.format("Не удалось создать папку %s\n", nameDir);
    }

    public static String createFileLog(String nameFile, boolean flag) {
        return flag ? String.format("Файл %s был создан успешно\n", nameFile) : String.format("Не удалось создать файл %s\n", nameFile);
    }

    public static void createDirs(List<String> listDirs, String pathDir) {
        for (String item : listDirs) {
            File dir = new File(pathDir + "/" + item);
            if (dir.mkdir()) {
                sb.append(createDirLog(item, true));
            } else {
                sb.append(createDirLog(item, false));
            }
        }
    }

    public static void createFiles(List<String> listFiles, String pathDir) {
        for (String item : listFiles) {
            File file = new File(pathDir + "/" + item);
            try {
                if (file.createNewFile()) {
                    sb.append(createFileLog(item, true));
                } else {
                    sb.append(createFileLog(item, false));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void writerLogs(String pathFile, String textLogs) {
        File file = new File(pathFile);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(textLogs);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
