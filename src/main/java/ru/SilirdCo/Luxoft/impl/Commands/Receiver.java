package ru.SilirdCo.Luxoft.impl.Commands;

import ru.SilirdCo.Luxoft.impl.Entities.Network;

public class Receiver {
    private final Network network;

    public Receiver(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }
}
