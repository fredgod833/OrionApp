package com.mddinfrastructure.mapper;

public interface EntityMapper <E, D> {
    E toDomain(D dto);
    D toPresenter(E entity);
}
