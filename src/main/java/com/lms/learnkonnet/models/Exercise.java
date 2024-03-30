package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.ExerciseType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private String hashPassword;

    @Column(name = "salt", nullable = true, unique = true)
    private String salt;

    @Column(name = "is_graded", nullable = false)
    private Boolean isGraded = false ;

    @Column(name = "factor", nullable = true)
    private Float factor = (float) 1 ;

    @Column(name = "is_reviewed", nullable = false)
    private Boolean isReviewed = false ;

    @Column(name = "is_show_score", nullable = false)
    private Boolean isShowScore = false ;

    @Column(name = "is_show_answer", nullable = false)
    private Boolean isShowAnswer = false;

    @Column(name =  "duration", nullable = true)
    private Integer duration;

    @Column(name = "started_at", nullable = true)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = true)
    private Timestamp endedAt;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private Status status;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
//    private List<ExerciseSection> sections;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
//    private List<ExerciseLog> exerciseLogs;
//
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
    private Quiz quiz;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
    private Assignment assignment;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
//    private List<ExerciseStudent> students;
//
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exercise")
//    private PostExercise post;

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
