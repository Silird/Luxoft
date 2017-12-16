package ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.Services;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;

import java.util.List;

public interface IService<Data> {

    /**
     * Получить все элементы справочника
     * @return - результат асинхронного вычисления
     */
    List<Data> getElements();

    Data getFirstResult();

    /**
     * Поиск элемента справочника по Id
     * @param id - Id  искомого элемента
     * @return - результат асинхронного вычисления
     */
    Data findById(Integer id);

    /**
     * Поиск элемента справочника по атрибуту
     * @param attributes - список атрибутов для поиска
     * @return - результат асинхронного вычисления
     */
    List<Data> findByAttribute(List<ElementAttribute> attributes);

    /**
     * Сохрание элемента справочника
     * @param entity - сущность для сохранения
     * @return - результат асинхронного вычисления
     */
    Data save(Data entity);

    List<Data> save(List<Data> entities);

    /**
     * Удаление элемента
     * @param id  - идентификатор элемента для удаления
     * @return - результат асинхронного вычисления
     */
    Boolean remove(Integer id);

    /**
     * Удаление списка элементов
     * @param list - список идентификаторов элементов для удаления
     * @return - результат асинхронного вычисления
     */
    Boolean removeList(List<Integer> list);
}
