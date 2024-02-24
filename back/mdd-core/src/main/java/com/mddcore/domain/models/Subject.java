package com.mddcore.domain.models;

import java.util.List;

public class Subject {
    private Identity id;
    private String name;
    private List<Article> articleList;

    public Subject(Identity id, String name, List<Article> articleList) {
        this.id = id;
        this.name = name;
        this.articleList = articleList;
    }

    public Identity getId() {
        return id;
    }

    public void setId(Identity id) {
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
