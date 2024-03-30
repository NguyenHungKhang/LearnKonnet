package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITemplateRepository extends JpaRepository<Template, Long> {
}
