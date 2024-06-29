package com.dio.iphone.utils;

public class DataSanitizer {
    public static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }
}
