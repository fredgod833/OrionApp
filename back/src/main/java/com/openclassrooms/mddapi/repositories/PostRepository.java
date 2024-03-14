package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> getByThemeIn(List<Theme> themes);
}
