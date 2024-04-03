package com.lms.learnkonnet.dtos.responses.relations;

import com.lms.learnkonnet.dtos.responses.notification.NotificationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserNotificationResponseDto {
    private Long id;
    private Long userId;
    private NotificationResponseDto notification;
    private Boolean isChecked;
    private Boolean isSeen;
}
