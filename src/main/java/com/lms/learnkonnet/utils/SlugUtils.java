package com.lms.learnkonnet.utils;
public class SlugUtils {
    public static String generateSlug(String input) {
        String slug = input.toLowerCase().trim();
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.replaceAll("[^a-z0-9\\-]", "");
        slug = slug.replaceAll("^-+|-+$", "");
        if (slug.length() > 80) {
            slug = slug.substring(0, 80);
        }
        return slug;
    }
}