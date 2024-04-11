package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.QuizGradedType;
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

    @Column(name = "is_mix_question", nullable = false)
    private Boolean isMixQuestion = false;

    @Column(name = "is_mix_answer", nullable = false)
    private Boolean isMixAnswer = false;

    @Column(name = "is_limit_times_todo", nullable = false)
    private Boolean isLimitAttempts = false;

    @Column(name = "times_to_do", nullable = true)
    private Integer attempts = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "graded_type", nullable = false)
    private QuizGradedType gradedType = QuizGradedType.HIGHEST_SCORE;

    @Column(name = "is_limit_number_of_question", nullable = false)
    private Boolean isLimitNumberOfQuestion = false;

    @Column(name = "number_of_question", nullable = true)
    private Integer numberOfQuestion;

    @Column(name = "is_question_level_classification", nullable = false)
    private Boolean isQuestionLevelClassification = false;

    @Column(name = "nums_of_lvl_1", nullable = true)
    private Integer numsOfLvl1 = 0;

    @Column(name = "nums_of_lvl_2", nullable = true)
    private Integer numsOfLvl2 = 0;

    @Column(name = "nums_of_lvl_3", nullable = true)
    private Integer numsOfLvl3 = 0;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Question> questions;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
