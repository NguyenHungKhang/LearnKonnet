package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaterialRepository extends JpaRepository<Material, Long> {
}
