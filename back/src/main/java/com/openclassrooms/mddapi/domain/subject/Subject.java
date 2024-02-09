package com.openclassrooms.mddapi.domain.subject;

import com.openclassrooms.mddapi.domain.article.Article;

import java.util.List;

public class Subject {
    private Long id;
    private String name;
    private List<Article> articleList;

    public Subject(Long id, String name, List<Article> articleList) {
        this.id = id;
        this.name = name;
        this.articleList = articleList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
