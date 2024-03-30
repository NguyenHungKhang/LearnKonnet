package com.lms.learnkonnet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "quiz")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_mix_question", nullable = false)
    private Boolean isMixQuestion = false;

    @Column(name = "is_mix_answer", nullable = false)
    private Boolean isMixAnswer = false;

    @Column(name = "is_limit_times_todo", nullable = false)
    private Boolean isLimitTimesToDo = false;

    @Column(name = "times_to_do", nullable = true)
    private Integer timestodo = 1;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "graded_type", nullable = false)
//    private QuizGradedType gradedType = QuizGradedType.FIRST_TIME;

    @Column(name = "is_limit_number_of_question", nullable = false)
    private Boolean isLimitNumberOfQuestion = false;

    @Column(name = "is_question_level_classification", nullable = false)
    private Boolean isQuestionLevelClassification = false;

    @Column(name = "is_mix_with_exercise_code", nullable = false)
    private Boolean isMixWithExerciseCode = false;

    @Column(name = "max_nums_of_exercise_code", nullable = true)
    private Integer maxNumsOfExerciseCode;

    @Column(name = "nums_of_lvl_1", nullable = true)
    private Integer numsOfLvl1 = 0;

    @Column(name = "nums_of_lvl_2", nullable = true)
    private Integer numsOfLvl2 = 0;

    @Column(name = "nums_of_lvl_3", nullable = true)
    private Integer numsOfLvl3 = 0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Template> templates;

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
