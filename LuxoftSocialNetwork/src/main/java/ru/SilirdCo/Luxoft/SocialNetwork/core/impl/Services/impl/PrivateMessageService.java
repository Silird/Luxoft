/*
==========================================================
Скелет файла создан через генератор кода
Файл изменять можно
==========================================================

*/


package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PrivateMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.BaseService;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;

/**
 * Реализация доступа к базе данных для сущностей PrivateMessage
 * @see PrivateMessage
 */
public class PrivateMessageService extends BaseService<PrivateMessage> {

    public PrivateMessageService() {
        super(DAOFactory.getInstance()
                .getPrivateMessageDAO());
    }

}