package com.mddcore.usecases.mapper;

import java.util.List;

public interface EntityMapper <E, D> {
    E toDomain(D dto);
    D toPresenter(E entity);
    List<E> toDomain(List<D> dto);
    List<D> toPresenter(List<E> entity);
}

