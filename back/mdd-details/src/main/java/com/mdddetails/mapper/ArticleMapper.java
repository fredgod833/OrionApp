package com.mdddetails.mapper;

import com.mddcore.domain.models.Article;
import com.mdddetails.models.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, CommentMapper.class})
public interface ArticleMapper extends EntMapper<Article, ArticleEntity> {
    Article toDomain(ArticleEntity entity);
}
