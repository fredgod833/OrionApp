package com.openclassrooms.mddapi.application.article;

import com.openclassrooms.mddapi.application.EntityMapper;
import com.openclassrooms.mddapi.application.article.dto.ArticleDto;
import com.openclassrooms.mddapi.domain.article.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<Article, ArticleDto> {
}
