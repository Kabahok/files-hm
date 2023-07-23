import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

          List<GameProgress> gameProgressList = Arrays.asList(
                  new GameProgress(123,5657,45646, 4.5),
                  new GameProgress(546,456,345, 5.6),
                  new GameProgress(3454,243,577, 1.6)
          );

          for (int i = 0; i < gameProgressList.size(); i++) {
              saveGame(String.format("%s/src/Games/savegames/save%d.txt", curDir, i+1), gameProgressList.get(i));
          }

        zip(String.format("%s/src/Games/savegames", curDir), "zip", Arrays.asList(new File(String.format("%s/src/Games/savegames", curDir)).list()));

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

    public static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zip(String pathDir, String nameZip, List<String> listFiles) {

            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(String.format("%s/%s.zip", pathDir, nameZip)))) {

               for (String item : listFiles) {
                    if (item.contains(".txt")) {
                        ZipEntry entry = new ZipEntry(item);
                        zout.putNextEntry(entry);

                        FileInputStream fis = new FileInputStream(String.format("%s/%s", pathDir, item));
                        byte[] buffer = new byte[fis.available()];
                        zout.write(buffer);

                        File file = new File(String.format("%s/%s", pathDir, item));
                        file.delete();

                        fis.close();
                        zout.closeEntry();

                    }
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
