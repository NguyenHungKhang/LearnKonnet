package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.File;
import com.lms.learnkonnet.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepository extends JpaRepository<File, Long> {
}
