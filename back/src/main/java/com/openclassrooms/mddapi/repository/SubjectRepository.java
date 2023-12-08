package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Access subject data
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
