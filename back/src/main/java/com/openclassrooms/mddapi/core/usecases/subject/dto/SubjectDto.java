package com.openclassrooms.mddapi.core.usecases.subject.dto;

import com.openclassrooms.mddapi.core.domain.models.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectDto {
    private String name;
    private List<Article> articleList;
}
