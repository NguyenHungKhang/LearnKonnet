package com.lms.learnkonnet.models.relations;

import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Section;
import com.lms.learnkonnet.models.enums.MemberSectionStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "member_section")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // update status
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MemberSectionStatus status = MemberSectionStatus.NOT_COMPLETED;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
