package com.lms.learnkonnet.dtos.responses.notification;

import com.lms.learnkonnet.models.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationResponseDto {
    private Long id;
    private String title;
    private String message;
    private String url;
    private NotificationType notificationType;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
