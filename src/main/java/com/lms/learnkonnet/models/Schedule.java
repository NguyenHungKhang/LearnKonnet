package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.ClassType;
import com.lms.learnkonnet.models.enums.ScheduleRepeatType;
import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "desc", nullable = true)
    private String desc;

    @Column(name = "link", nullable = false)
    private String link;


    @Enumerated(EnumType.STRING)
    @Column(name = "repeat_type", nullable = false)
    private ScheduleRepeatType repeatType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ClassType type;

    @Column(name = "repeat_day", nullable = false)
    private Integer repeatDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by")
    private Member createdByMember;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "updated_by")
    private Member updatedByMember;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
