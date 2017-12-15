package ru.SilirdCo.Luxoft.view.impl.Commands;

import ru.SilirdCo.Luxoft.core.impl.Entities.Network;

public class Receiver {
    private final Network network;

    public Receiver(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }
}
