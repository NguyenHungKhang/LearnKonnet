package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
