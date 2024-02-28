package com.mdddetails.repository.subject;

import com.mdddetails.models.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectJpaRepo extends JpaRepository<SubjectEntity, Long> {
}
