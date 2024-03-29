package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.MemberType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "section")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", nullable = true)
    private String desc;

    @Column(name = "is_contain_material", nullable = false)
    private Boolean isContainMaterial = false;

    @Column(name = "is_contain_exercise", nullable = false)
    private Boolean isContainExercise = false;

    @Column(name = "is_in_topic", nullable = false)
    private Boolean isInTopic = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "topic_id", nullable = true)
    private Topic topic;

    @Column(name = "order_in_topic", nullable = true)
    private Long orderInTopic;

    @Column(name = "started_at", nullable = true)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = true)
    private Timestamp endedAt;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private Status status;
//
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
//    private SectionTopic topic;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
//    private List<MaterialSection> materials;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
//    private List<ExerciseSection> exercises;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
//    private List<SectionStudent> students;

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
