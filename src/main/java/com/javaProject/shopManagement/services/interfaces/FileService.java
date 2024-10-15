package com.javaProject.shopManagement.services.interfaces;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public interface FileService {

    public File chooseImageFile(Stage stage);

    public String uploadImage(File file) throws IOException;

}
