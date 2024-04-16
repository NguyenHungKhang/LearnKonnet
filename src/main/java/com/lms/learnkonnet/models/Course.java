package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", nullable = true)
    private String desc;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "cover", nullable = true)
    private String cover;

    @Column(name = "is_approve_students", nullable = false)
    private Boolean isApproveStudents;

    @Column(name = "is_prevent_students", nullable = false)
    private Boolean isPreventStudents;

    @Column(name = "is_show_score", nullable = false)
    private Boolean isShowScore;

    @Column(name = "is_student_allow_post", nullable = false)
    private Boolean isStudentAllowPost;

    @Column(name = "is_student_allow_comment", nullable = false)
    private Boolean isStudentAllowComment;

    @Column(name = "started_at", nullable = false)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = false)
    private Timestamp endedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
    private List<Member> members;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
    private List<Topic> topic;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
    private List<Post> posts;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
