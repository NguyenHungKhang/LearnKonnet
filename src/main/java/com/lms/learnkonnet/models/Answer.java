package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.relations.MemberExercise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "answer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_student_id", nullable = false)
    private MemberExercise memberExercise;

    @Column(name = "question_pattern", nullable = false)
    private String questionPattern;

    @Column(name = "answer_pattern", nullable = false)
    private String answerPattern;

    @Column(name = "score", nullable = false)
    private Float score;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
    private List<QuizAnswer> quizAnswers;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
//    private List<QuizAnswerShortText> shortTexts;
//
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
    private List<AssignmentAnswer> assignmentAnswers;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
//    private List<AssignmentAnswerFile> files;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
//    private List<AssignmentAnswerImage> images;

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
