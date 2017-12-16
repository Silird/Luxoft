package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.impl;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Services.BaseService;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.DAOFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.interfaces.DAO.IDAO;

public class User1Service extends BaseService<User1> {
    public User1Service() {
        super(DAOFactory.getInstance()
                .getUser1DAO());
    }
}
