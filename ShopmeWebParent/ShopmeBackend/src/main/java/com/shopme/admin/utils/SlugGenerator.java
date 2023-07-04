package com.shopme.admin.utils;

import java.text.Normalizer;

public class SlugGenerator {
    public static String generateSlug(String input) {
        String slug = Normalizer.normalize(input, Normalizer.Form.NFD); // bo dau utf-8
        slug = slug.toLowerCase()
                .replaceAll("\\s+", "-") // Thay thế khoảng trắng bằng dấu gạch ngang
                .replaceAll("[^a-zA-Z0-9-]", ""); // Loại bỏ các ký tự không hợp lệ
        return slug;
    }
}
