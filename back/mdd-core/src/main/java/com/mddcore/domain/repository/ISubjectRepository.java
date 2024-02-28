package com.mddcore.domain.repository;

import com.mddcore.domain.models.Subject;

import java.util.List;
import java.util.Optional;

public interface ISubjectRepository {
    List<Subject> findAll();
    Optional<Subject> findById(Long id);
}
