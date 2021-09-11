package com.api.dao;

import java.util.LinkedHashSet;

/**
 * Дженерик DAO класс
 * @param <E> - Тип сущности
 * @param <ID> - Тип первичного ключа сущности
 */
public interface GenericDao<E, ID> {

    E save(E entity);

    void saveAll(LinkedHashSet<E> entities);

    LinkedHashSet<E> findAll();

    E findById(ID id);

    boolean update(E entity);

    boolean delete(E entity);

    boolean deleteById(ID id);

    void deleteAll();

}
