package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.User1Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Network;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.User;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

public class CreateNewUserCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final User1Receiver user1Receiver;

    public CreateNewUserCommand(CommonReceiver commonReceiver, User1Receiver user1Receiver) {
        this.commonReceiver = commonReceiver;
        this.user1Receiver = user1Receiver;

        numberArgs = 3;
    }

    @Override
    public void execute(String[] args) {
        if (!check(args)) {
            commonReceiver.commandTemplate("логин пароль повторение_пароля");
            return;
        }

        if (!args[1].equals(args[2])) {
            commonReceiver.sendWarn("Введённый пароли не совпадают");
            return;
        }

        commonReceiver.sendMessage("Создание пользователя");

        User1 user1 = new User1();
        user1.setId(0);
        user1.setLogin(args[0]);
        user1.setPassword(args[1]);

        user1 = ServiceFactory.getInstance()
                .getUser1Service()
                .save(user1);

        user1Receiver.createdUser(user1);
    }
}
