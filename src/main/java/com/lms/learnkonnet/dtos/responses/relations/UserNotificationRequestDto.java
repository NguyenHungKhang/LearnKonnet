package com.lms.learnkonnet.dtos.responses.relations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserNotificationRequestDto {
    private Long userId;
    private Long notificationId;
    private Boolean isChecked;
    private Boolean isSeen;
}
