package com.lms.learnkonnet.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileUtils {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    private Cloudinary cloudinary;

    private static final Map<String, String> FILE_EXTENSION_MAP;

    static {
        FILE_EXTENSION_MAP = new HashMap<>();
        FILE_EXTENSION_MAP.put("image/jpeg", "jpg");
        FILE_EXTENSION_MAP.put("image/png", "png");
        FILE_EXTENSION_MAP.put("image/gif", "gif");
        FILE_EXTENSION_MAP.put("video/mp4", "mp4");
        FILE_EXTENSION_MAP.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        FILE_EXTENSION_MAP.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
        FILE_EXTENSION_MAP.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
        FILE_EXTENSION_MAP.put("text/plain", "txt");
        // Thêm các loại tệp khác nếu cần thiết
    }

    public FileUtils() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public String determineFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null && FILE_EXTENSION_MAP.containsKey(contentType)) {
            return FILE_EXTENSION_MAP.get(contentType);
        }
        return null;
    }

    public boolean isValidFileType(MultipartFile file) {
        return determineFileType(file) != null;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        try {
            if (!isValidFileType(file)) {
                throw new IllegalArgumentException("Unsupported file type");
            }
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage());
        }
    }

    public String updateFile(MultipartFile file, String publicId) throws IOException {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "public_id", publicId,
                    "overwrite", true
            );
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Error updating file: " + e.getMessage());
        }
    }

    public void deleteFile(String publicId) throws IOException {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + e.getMessage());
        }
    }
}
