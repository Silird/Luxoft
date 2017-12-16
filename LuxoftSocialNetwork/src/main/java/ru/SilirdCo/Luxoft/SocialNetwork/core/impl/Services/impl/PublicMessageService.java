/*
==========================================================
Скелет файла создан через генератор кода
Файл изменять можно
==========================================================

*/


package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PublicMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.BaseService;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;

/**
 * Реализация доступа к базе данных для сущностей PublicMessage
 * @see PublicMessage
 */
public class PublicMessageService extends BaseService<PublicMessage> {

    public PublicMessageService() {
        super(DAOFactory.getInstance()
                .getPublicMessageDAO());
    }

}