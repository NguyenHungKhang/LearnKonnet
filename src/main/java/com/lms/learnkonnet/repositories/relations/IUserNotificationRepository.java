package com.lms.learnkonnet.repositories.relations;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.relations.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserNotificationRepository extends JpaRepository<UserNotification, Long> {
}
