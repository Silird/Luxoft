package ru.SilirdCo.Luxoft.interfaces.DAO;

public interface IDAO<T> {
    void save(T entity);

    void getById(Integer id);

    void getAll();
}
