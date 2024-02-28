package com.mdddetails.mapper;

import com.mddcore.domain.models.Article;
import com.mdddetails.models.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntMapper<Article, ArticleEntity> {
}
