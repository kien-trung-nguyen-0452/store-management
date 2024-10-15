package com.javaProject.shopManagement.services;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public interface FileService {

    public File chooseImageFile(Stage stage);

    public String uploadImage(File file) throws IOException;

}
