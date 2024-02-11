package com.openclassrooms.p6.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.openclassrooms.p6.model.Articles;
import com.openclassrooms.p6.payload.response.ArticleSummary;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mappings({
            @Mapping(target = "id", ignore = false),
            @Mapping(target = "publicationDate", source = "createdAt"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "articleId", source = "id")
    })
    ArticleSummary toDtoArticle(Articles article);

    Iterable<ArticleSummary> toDtoArticles(Iterable<ArticleSummary> articlesDto);
}
