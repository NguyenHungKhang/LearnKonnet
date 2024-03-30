package com.lms.learnkonnet.repositories.relations;

import com.lms.learnkonnet.models.relations.MaterialSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaterialSectionRepository extends JpaRepository<MaterialSection, Long> {
}
