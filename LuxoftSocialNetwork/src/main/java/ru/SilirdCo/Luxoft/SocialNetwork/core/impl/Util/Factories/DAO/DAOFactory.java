package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.DAO.Generated.DAOFactoryGenerated;

public class DAOFactory extends DAOFactoryGenerated {
    private static DAOFactory instance;

    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }
}
