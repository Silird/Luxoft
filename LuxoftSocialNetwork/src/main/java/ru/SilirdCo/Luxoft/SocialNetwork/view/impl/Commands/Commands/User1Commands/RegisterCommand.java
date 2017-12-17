package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.User1Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.AttributeType;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.User1Attribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public class RegisterCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final User1Receiver user1Receiver;

    public RegisterCommand(CommonReceiver commonReceiver, User1Receiver user1Receiver) {
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

        List<ElementAttribute> attributes = new ArrayList<>();
        attributes.add(User1Attribute.getAttribute(AttributeType.EQUAL.getID(), User1Attribute.LOGIN, args[0]));

        List<User1> user1s = ServiceFactory.getInstance()
                .getUser1Service()
                .findByAttribute(attributes);

        User1 registerUser;
        if ((user1s == null) || (user1s.isEmpty())) {
            registerUser = null;
        }
        else {
            registerUser = user1s.get(0);
        }

        if (registerUser == null) {
            User1 user1 = new User1();
            user1.setId(0);
            user1.setLogin(args[0]);
            user1.setPassword(args[1]);

            user1 = ServiceFactory.getInstance()
                    .getUser1Service()
                    .save(user1);

            user1Receiver.createdUser(user1);
        }
        else {
            commonReceiver.sendWarn("Пользователь с логином \"" + args[0] + "\" уже зарегестрирован");
        }
    }
}
