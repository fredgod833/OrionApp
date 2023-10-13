package com.openclassrooms.mddapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.models.User;

@Repository
public interface UserIRepository extends JpaRepository<User, Long> {

}
