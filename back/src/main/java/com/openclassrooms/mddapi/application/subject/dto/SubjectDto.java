package com.openclassrooms.mddapi.application.subject.dto;

import com.openclassrooms.mddapi.domain.article.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectDto {
    private String name;
    private List<Article> articleList;
}
