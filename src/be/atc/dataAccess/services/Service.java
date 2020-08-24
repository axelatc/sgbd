package be.atc.dataAccess.services;

import java.util.Collection;

public interface Service<T> {

    boolean hasDuplicate(T t);

    T findByIdOrNull(int id);

    Collection<T> findAllOrNull();

    void save(T t);

    void update(T t);

    void delete(T t);
}
