package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}

