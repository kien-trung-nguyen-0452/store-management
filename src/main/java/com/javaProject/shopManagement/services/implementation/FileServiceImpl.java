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
            Path destPath = Paths.get(uploadDir, fileName);
            Files.copy(file.toPath(), destPath);
            return destPath.toString();
        }
        return null;
    }
}