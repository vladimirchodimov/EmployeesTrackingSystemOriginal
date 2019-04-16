package controller;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileContentsReader {
    public static final String Clients_FILE_NAME = "clients.txt";
    private String fileName;
    private String fileContents;

    public FileContentsReader(String fileName) {
        this.fileName = fileName;
    }

    public String fetchAllRecordsFromFile() {
        Path patToFile = Paths.get(fileName);
        if (Files.notExists(patToFile) || patToFile.toFile().length() == 0) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Няма намерен файл.");
            a.setTitle("Търсене на файл.");
            a.showAndWait();
            return "";
        } else {
            File file = new File(fileName);
            StringBuilder fileRecords = new StringBuilder();
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileRecords.append(line + "\n");
            }
            String allRecordsString = fileRecords.toString();
            scanner.close();
            return allRecordsString;
        }
    }
}
