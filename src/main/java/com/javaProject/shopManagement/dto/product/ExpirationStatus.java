package com.javaProject.shopManagement.dto.product;

import javafx.scene.image.Image;

import java.util.Objects;

public enum ExpirationStatus {
    EXPIRED("/com/javaProject/shopManagement/public/icon/label-icon/expired.png"),
    EXPIRING_SOON("/com/javaProject/shopManagement/public/icon/label-icon/expiring_soon.png");

    private final String imagePath;
    ExpirationStatus(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
    }
}
