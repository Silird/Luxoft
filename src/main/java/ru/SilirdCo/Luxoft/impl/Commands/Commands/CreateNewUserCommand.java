package ru.SilirdCo.Luxoft.impl.Commands.Commands;

import ru.SilirdCo.Luxoft.impl.Commands.Receiver;
import ru.SilirdCo.Luxoft.impl.Entities.Network;
import ru.SilirdCo.Luxoft.impl.Entities.User;
import ru.SilirdCo.Luxoft.impl.Messages.EventSender;
import ru.SilirdCo.Luxoft.interfaces.Commands.ICommand;

public class CreateNewUserCommand implements ICommand {
    private final Receiver receiver;

    public CreateNewUserCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        EventSender.sendMessage("Создание пользователя");
        User user = new User();
        Network network = receiver.getNetwork();
        network.addUser(user);

        EventSender.sendMessage("Пользователь добавлен в сеть, количество пользователей в сети: " + network.getUsers().size());
    }
}
