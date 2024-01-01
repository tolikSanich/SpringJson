package com.example.springjson.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
@Component
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    Set<D> toDto(Set<E> entityList);
}
