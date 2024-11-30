package com.javaProject.shopManagement.services.implementation;

import com.javaProject.shopManagement.exception.GlobalExceptionHandler;
import com.javaProject.shopManagement.services.interfaces.FileService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    private static final String UPLOAD_DIR = "image";
    private static final String TEMP_DIR = "temp_img";

    private final Path uploadPath;
    private final Path tempPath;

    private static FileServiceImpl instance;

    private FileServiceImpl() {
        this.uploadPath = Paths.get(UPLOAD_DIR);
        this.tempPath = Paths.get(TEMP_DIR);
        createDirectories(uploadPath);
        createDirectories(tempPath);
    }

    public static synchronized FileServiceImpl getInstance() {
        if (instance == null) {
            instance = new FileServiceImpl();
        }
        return instance;
    }

    private void createDirectories(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            GlobalExceptionHandler.handleException(e);
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
    public String uploadTemporaryImage(File file) throws IOException {
        if (file == null) {
            return null;
        }

        // Generate a unique temporary file name
        String tempFileName = UUID.randomUUID().toString() + "_" + file.getName().replaceAll("\\s+", "_");
        Path tempFilePath = tempPath.resolve(tempFileName);

        // Copy file to temp directory
        Files.copy(file.toPath(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
        return tempFilePath.toString();
    }

    @Override
    public String uploadImage(String tempFilePath) throws IOException {
        if (tempFilePath == null || !Files.exists(Paths.get(tempFilePath))) {
            throw new IllegalArgumentException("Temporary file path is invalid or does not exist.");
        }

        Path tempFile = Paths.get(tempFilePath);
        String fileName = tempFile.getFileName().toString();
        Path destPath = uploadPath.resolve(fileName);

        // Avoid duplicate file names
        destPath = resolveUniqueFileName(destPath);

        // Move file from temp to upload directory
        Files.move(tempFile, destPath, StandardCopyOption.REPLACE_EXISTING);

        // Automatically clean up temporary files

        return destPath.toString();
    }

   /* @Override
    public void deleteUploadedFile(String uploadedFilePath) throws IOException {
        if (uploadedFilePath != null && Files.exists(Paths.get(uploadedFilePath))) {
            Files.delete(Paths.get(uploadedFilePath));
        }
    }*/

    private void deleteTemporaryImage(String tempFilePath) {
        try {
            if (tempFilePath != null && Files.exists(Paths.get(tempFilePath))) {
                Files.delete(Paths.get(tempFilePath));
            }
        } catch (IOException e) {
            GlobalExceptionHandler.handleException(e);
        }
    }

    @Override
    public void cleanTemporaryFiles() {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(tempPath)) {
            for (Path file : directoryStream) {
                Files.delete(file);
            }
        } catch (IOException e) {
            GlobalExceptionHandler.handleException(e);
        }
    }

    private Path resolveUniqueFileName(Path destPath) {
        if (Files.exists(destPath)) {
            String nameWithoutExt = destPath.getFileName().toString();
            String extension = "";
            int dotIndex = nameWithoutExt.lastIndexOf(".");
            if (dotIndex != -1) {
                extension = nameWithoutExt.substring(dotIndex);
                nameWithoutExt = nameWithoutExt.substring(0, dotIndex);
            }

            int count = 1;
            while (Files.exists(destPath)) {
                String newFileName = nameWithoutExt + "_" + count + extension;
                destPath = destPath.getParent().resolve(newFileName);
                count++;
            }
        }
        return destPath;
    }
}