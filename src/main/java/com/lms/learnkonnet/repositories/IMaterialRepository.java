package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByCourseIdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    Page<Material> findAllByMaterialSections_SectionIdAndNameContaining(Long sectionId, String name, Pageable pageable);
    List<Material> findAllByCourseId(Long courseId);
    List<Material> findAllByMaterialSections_SectionId(Long sectionId);
}
