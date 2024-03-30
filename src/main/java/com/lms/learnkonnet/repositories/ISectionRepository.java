package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISectionRepository extends JpaRepository<Section, Long> {
}
