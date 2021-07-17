package com.phonevalidator.mapper;

import java.util.List;

public interface BaseMapper<E, T> {

    List<T> toModels(List<E> entities);
}
