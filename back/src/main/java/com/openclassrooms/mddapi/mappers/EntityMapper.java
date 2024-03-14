package com.openclassrooms.mddapi.mappers;

import java.util.List;

public interface EntityMapper<D, E> {

    D toDTO(E entity);

    E toEntity(D dto);

    List<D> toDTO(List<E> entityList);

    List<E> toEntity(List<D> dtoList);
}
