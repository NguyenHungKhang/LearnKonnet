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
    private MemberAttempt memberAttempt;

    // Example ( // 3:4,1,3,2 // 7: 3,1,2 //)
    // With 3:, 7: is the number order of question in quizz
    // And 4,1,3,2 and 3,1,2 is number order of choice in quizz

    @Column(name = "quiz_pattern", nullable = false)
    private String quizattern;

    @Column(name = "score", nullable = false)
    private Float score;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
    private List<QuizAnswer> quizAnswers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
    private List<AssignmentAnswer> assignmentAnswers;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

}
