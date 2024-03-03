package com.mdddetails.mapper;

import com.mddcore.domain.models.Article;
import com.mdddetails.models.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class, CommentMapper.class})
public interface ArticleMapper extends EntMapper<Article, ArticleEntity> {
//    @Mapping(source = "subject", target = "subject")
//    @Mapping(source = "commentsList", target = "commentsList")
    Article toDomain(ArticleEntity entity);
}
