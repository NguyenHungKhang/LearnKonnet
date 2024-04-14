package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.ExerciseType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.models.relations.ExerciseSection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
@Entity
@Table(name = "exercise")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", nullable = true)
    private String desc;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_type", nullable = false)
    private ExerciseType exerciseType;

    @Column(name = "is_has_password", nullable = false)
    private Boolean isHasPassword = false;

    @Column(name = "hash_password", nullable = true)
    private String password;



    @Column(name =  "duration", nullable = true)
    private Integer duration;

    @Column(name = "started_at", nullable = false)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = false)
    private Timestamp endedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
    private List<ExerciseSection> sections;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
    private Quiz quiz;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
    private Assignment assignment;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
    private List<MemberAttempt> memberAttempts;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
