package controller;

import javafx.scene.control.Alert;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileContentsWriter {
    private String fileName;
    private String[] contents;

    public FileContentsWriter(String fileName, String[] contents) {
        this.fileName = fileName;
        this.contents = contents;
    }

    public void writeToFile() {
        try (
                PrintWriter writer = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(fileName, true)))) {

            for (int i = 0; i < contents.length; i++) {
                writer.print(contents[i] + "\t");
            }
            writer.print("\n");
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Записът е успешен.");
            a.setTitle("Запис в базата данни.");
            a.showAndWait();
            writer.close();
        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.WARNING, "За съжаление нещо се обърка.");
            a.setTitle("Запис в базата данни.");
            a.showAndWait();
        }
    }
}
