package com.javaProject.shopManagement.services;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    // Tạo thư mục upload nếu chưa tồn tại
    private void createUploadDirIfNotExists() {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Mở FileChooser để chọn file ảnh
    @Override
    public File chooseImageFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn ảnh");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        return fileChooser.showOpenDialog(stage);
    }

    // Upload ảnh vào thư mục dự án và trả về đường dẫn ảnh
    @Override
    public String uploadImage(File file) throws IOException {
        if (file != null) {
            // Tạo đường dẫn đích trong thư mục upload
            String fileName = file.getName();
            Path destPath = Paths.get(uploadDir, fileName);

            // Sao chép file từ máy người dùng vào thư mục upload
            Files.copy(file.toPath(), destPath);

            // Trả về đường dẫn tương đối của file sau khi upload
            return destPath.toString();
        }
        return null;
    }
}