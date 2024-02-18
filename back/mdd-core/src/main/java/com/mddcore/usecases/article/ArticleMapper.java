package com.mddcore.usecases.article;

import com.mddcore.domain.models.Article;
import com.mddcore.usecases.EntityMapper;
import com.mddcore.usecases.article.dto.ArticleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<Article, ArticleDto> {
}
