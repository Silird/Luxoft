package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities;

import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.Entities.IEntity;

public abstract class BaseEntity implements IEntity {
    /**
     * Конструктор сущности
     */
    public BaseEntity() {
    }

    /**
     * Получение Id сущности
     * @return - Id сущности
     */
    @Override
    public Integer getId() {
        return null;
    }

    /**
     * Установка Id сущности
     * @param id - устанавливаемый Id
     */
    @Override
    public void setId(Integer id) {
    }
}
