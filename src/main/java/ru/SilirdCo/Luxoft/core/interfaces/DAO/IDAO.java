package ru.SilirdCo.Luxoft.core.interfaces.DAO;

public interface IDAO<T> {
    void save(T entity);

    void getById(Integer id);

    void getAll();
}
