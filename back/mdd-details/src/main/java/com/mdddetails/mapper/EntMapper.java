package com.mdddetails.mapper;

import java.util.List;

public interface EntMapper <E, D> {
        E toDomain(D entity);
        D toEntity(E domain);
        List<E> toDomain(List<D> dto);
        List<D> toEntity(List<E> entity);
}

