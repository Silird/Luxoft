package ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.Generated.ServiceFactoryGenerated;

public class ServiceFactory extends ServiceFactoryGenerated {
    private static ServiceFactory instance;

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }
}
