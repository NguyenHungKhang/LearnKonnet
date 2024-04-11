package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.models.relations.ExerciseSection;
import com.lms.learnkonnet.models.relations.MaterialSection;
import com.lms.learnkonnet.models.relations.MemberSection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "topic_id", nullable = true)
    private Topic topic;

    @Column(name = "order", nullable = false)
    private Long order;

    @Column(name = "started_at", nullable = false)
    private Timestamp startedAt;

    @Column(name = "ended_at", nullable = false)
    private Timestamp endedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
    private List<MaterialSection> materials;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
    private List<ExerciseSection> exercises;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "section")
    private List<MemberSection> members;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
