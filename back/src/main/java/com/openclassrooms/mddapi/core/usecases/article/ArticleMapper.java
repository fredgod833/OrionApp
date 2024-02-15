package com.openclassrooms.mddapi.core.usecases.article;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.core.usecases.article.dto.ArticleDto;
import com.openclassrooms.mddapi.core.domain.models.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<Article, ArticleDto> {
}
