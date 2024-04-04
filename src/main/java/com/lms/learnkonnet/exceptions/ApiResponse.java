package com.lms.learnkonnet.exceptions;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private boolean success;
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}