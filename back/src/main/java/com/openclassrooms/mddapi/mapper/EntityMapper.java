package com.openclassrooms.mddapi.mapper;

import java.util.Collection;

/**
 * Interface standard de mapper Entity <-> DTO exploitée par MapStruct
 * @param <D> : type de donnée DTO
 * @param <E> : type de donnée Entitée
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    Collection<E> toEntity(Collection<D> dtoList);

    Collection<D> toDto(Collection<E> entityList);
}
