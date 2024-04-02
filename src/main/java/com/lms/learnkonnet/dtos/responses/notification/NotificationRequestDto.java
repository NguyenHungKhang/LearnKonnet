package com.lms.learnkonnet.dtos.responses.notification;

import com.lms.learnkonnet.models.enums.NotificationType;
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
