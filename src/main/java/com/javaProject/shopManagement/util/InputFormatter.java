package com.javaProject.shopManagement.util;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class InputFormatter {

    public static TextFormatter<Integer> getIntegerFormatter(int defaultValue) {
        return new TextFormatter<>(new IntegerStringConverter(), defaultValue, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        });
    }


    public static TextFormatter<Double> getDoubleFormatter(double defaultValue) {
        return new TextFormatter<>(new javafx.util.converter.DoubleStringConverter(), defaultValue, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;  // Cho phép số thực
            } else {
                return null;  // Ngăn chặn nếu không hợp lệ
            }
        });
    }

    // Bộ lọc giới hạn ký tự trong TextField
    public static TextFormatter<String> getLimitedTextFormatter(int maxLength) {
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= maxLength) {
                return change;  // Cho phép nếu số ký tự không vượt quá giới hạn
            } else {
                return null;  // Ngăn chặn thay đổi
            }
        });
    }
}
