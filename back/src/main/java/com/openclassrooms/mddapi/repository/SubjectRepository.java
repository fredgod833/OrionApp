package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

//    @Query(value = "SELECT * FROM post INNER JOIN subject WHERE id_subject=1", nativeQuery = true)
    List<Subject> findByidSubject(int id_subject);
}
