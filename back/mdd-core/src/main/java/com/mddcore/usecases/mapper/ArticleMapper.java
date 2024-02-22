package com.mddcore.usecases.mapper;

import com.mddcore.domain.models.Article;
import com.mddcore.usecases.dto.ArticleDto;
import org.mapstruct.Mapper;

@Mapper
public interface ArticleMapper extends EntityMapper<Article, ArticleDto> {
}
