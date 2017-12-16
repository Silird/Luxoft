/*
==========================================================
Скелет файла создан через генератор кода
Файл изменять можно
==========================================================

*/


package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.Profile;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.BaseService;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;

/**
 * Реализация доступа к базе данных для сущностей Profile
 * @see Profile
 */
public class ProfileService extends BaseService<Profile> {

    public ProfileService() {
        super(DAOFactory.getInstance()
                .getProfileDAO());
    }

}