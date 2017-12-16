package ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.DAO;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.BaseEntity;

import javax.persistence.PersistenceException;
import java.util.List;

public interface IDAO<Data extends BaseEntity> {

    /**
     * Обновление существующей сущности
     * @param entity - сущность, которую надо обновить (обновляется поле с таким же id)
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    Data update(Data entity) throws PersistenceException;

    /**
     * Удаление данной сущности
     * @param entity - сущность для удаления (удаляется поле с таким же id)
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    void remove(Data entity) throws PersistenceException;

    /**
     * Удаление данной сущности
     * @param id - идентификатор сущности для удаления
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    void remove(Integer id) throws PersistenceException;

    /**
     * Получение сущности по идентификатору
     * @param id - идентификатор сущности для поиска
     * @return - объекта (банк) с данным идентификатором
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    Data getByID(Integer id) throws PersistenceException;

    Data getFirstResult() throws PersistenceException;

    /**
     * Получение списка всех сущностей
     * @return - список всех сущностей
     * @throws PersistenceException - ошибка при запросе к базе данных
     */
    List<Data> getAll() throws PersistenceException;

    /**
     * Получение сущности по атрибуту
     * @return
     * @throws PersistenceException
     */

    List<Data> getByAttribute(List<ElementAttribute> attribute) throws PersistenceException;

}
