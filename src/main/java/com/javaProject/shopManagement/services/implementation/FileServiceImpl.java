package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.services.interfaces.FileService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceImpl implements FileService {

    public static FileServiceImpl getInstance() {
        return new FileServiceImpl();
    }

    private final String uploadDir;

    public FileServiceImpl() {
        this.uploadDir = "image";
        createUploadDirIfNotExists();
    }

    private void createUploadDirIfNotExists() {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                GlobalExceptionHandler.handleException(e);
            }
        }
    }

    @Override
    public File chooseImageFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        return fileChooser.showOpenDialog(stage);
    }

    @Override
    public String uploadImage(File file) throws IOException {
        if (file != null) {
            String fileName = file.getName();


            fileName = fileName.replaceAll("\\s+", "_");

            if (fileName.isEmpty()) {
                throw new IllegalArgumentException("File name cannot be empty after trimming whitespace.");
            }

            Path destPath = Paths.get(uploadDir, fileName);


            if (Files.exists(destPath)) {
                String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
                String extension = fileName.substring(fileName.lastIndexOf('.'));
                int count = 1;

                while (Files.exists(destPath)) {
                    String newFileName = nameWithoutExt + "_" + count + extension;
                    destPath = Paths.get(uploadDir, newFileName);
                    count++;
                }
            }

            Files.copy(file.toPath(), destPath);
            return destPath.toString();
        }
        return null;
    }
}