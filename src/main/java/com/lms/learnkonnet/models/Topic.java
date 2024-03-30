package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "topic")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "order", nullable = false)
    private Long order;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", nullable = true)
    private String desc;

    @Column(name = "is_announced", nullable = false)
    private Boolean isAnnounced = false;

    @Column(name = "started_at", nullable = true)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = true)
    private Timestamp endedAt;

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
