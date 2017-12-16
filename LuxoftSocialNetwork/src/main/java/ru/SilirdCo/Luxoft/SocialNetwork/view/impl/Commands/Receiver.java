package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Network;

public class Receiver {
    private final Network network;

    public Receiver(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }
}
