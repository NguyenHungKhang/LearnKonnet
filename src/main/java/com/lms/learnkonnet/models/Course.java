package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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

    @Column(name = "is_approve_students", nullable = true)
    private Boolean isApproveStudents;

    @Column(name = "is_prevent_students", nullable = true)
    private Boolean isPreventStudents;

    @Column(name = "is_show_score", nullable = true)
    private Boolean isShowScore;

    @Column(name = "is_student_allow_post", nullable = true)
    private Boolean isStudentAllowPost;

    @Column(name = "is_student_allow_comment", nullable = true)
    private Boolean isStudentAllowComment;

    @Column(name = "started_at", nullable = true)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = true)
    private Timestamp endedAt;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private Status status;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
//    private List<Member> members;
//
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
//    private List<Post> posts;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
