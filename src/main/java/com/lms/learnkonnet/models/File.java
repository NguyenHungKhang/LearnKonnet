package com.lms.learnkonnet.models;

import com.lms.learnkonnet.models.enums.FileType;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.QuizQuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "file")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Column(name = "url", nullable = false)
    private String url;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
    private Material material;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
    private AssignmentAnswer assignmentAnswer;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    private List<AssignmentMaterial> assignmentMaterials;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
