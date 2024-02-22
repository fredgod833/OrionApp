package com.mddcore.usecases.dto;

import com.mddcore.domain.models.Article;
import java.util.List;

public class SubjectDto {
    private String name;
    private List<Article> articleList;

    public SubjectDto(String name, List<Article> articleList) {
        this.name = name;
        this.articleList = articleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
