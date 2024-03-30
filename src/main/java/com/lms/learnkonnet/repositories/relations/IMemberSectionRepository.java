package com.lms.learnkonnet.repositories.relations;

import com.lms.learnkonnet.models.relations.MemberSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberSectionRepository extends JpaRepository<MemberSection, Long> {
}
