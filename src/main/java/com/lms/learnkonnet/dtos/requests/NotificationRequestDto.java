package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationRequestDto {
    private String title;
    private String message;
    private String url;
    private NotificationType notificationType;
}
