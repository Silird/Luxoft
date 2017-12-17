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

public class LoginCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final User1Receiver user1Receiver;

    public LoginCommand(CommonReceiver commonReceiver, User1Receiver user1Receiver) {
        this.commonReceiver = commonReceiver;
        this.user1Receiver = user1Receiver;

        numberArgs = 2;
    }

    @Override
    public void execute(String[] args) {
        if (!check(args)) {
            commonReceiver.commandTemplate("[логин или -id идентификатор_пользователя] пароль");
            return;
        }

        boolean findId;
        User1 user1;
        String password;
        if (args[0].equals("-id")) {
            findId = true;
            if ((args.length < 3) || (args[2] == null)) {
                commonReceiver.commandTemplate("[логин или -id идентификатор_пользователя] пароль");
                return;
            }

            String idStr = args[1];
            Integer id;
            try {
                id = Integer.parseInt(idStr);
            }
            catch (NumberFormatException ex) {
                commonReceiver.sendWarn("Идентификатор пользователя должен быть числом, найдено: " + idStr);
                return;
            }

            user1 = ServiceFactory.getInstance()
                    .getUser1Service()
                    .findById(id);

            password = args[2];
        }
        else {
            findId = false;
            String login = args[0];

            List<ElementAttribute> attributes = new ArrayList<>();
            attributes.add(User1Attribute.getAttribute(AttributeType.EQUAL.getID(), User1Attribute.LOGIN, login));

            List<User1> user1s = ServiceFactory.getInstance()
                    .getUser1Service()
                    .findByAttribute(attributes);

            if ((user1s == null) || (user1s.isEmpty())) {
                user1 = null;
            }
            else {
                user1 = user1s.get(0);
            }

            password = args[1];
        }

        if (user1 == null) {
            if (findId) {
                commonReceiver.sendWarn("Пользователь с идентификатором \"" + args[1] + "\" не найден");
            }
            else {
                commonReceiver.sendWarn("Пользователь с логином \"" + args[0] + "\" не найден");
            }
        }
        else {
            if (password.equals(user1.getPassword())) {
                user1Receiver.authUser(user1);
            }
            else {
                commonReceiver.sendWarn("Неверно введён пароль, проверьте раскладку клавиатуры или клавишу Caps-Lock");
            }
        }
    }
}
