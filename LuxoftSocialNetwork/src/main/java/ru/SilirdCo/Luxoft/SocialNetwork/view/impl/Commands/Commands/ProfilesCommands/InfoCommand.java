package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.ProfilesCommands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.AttributeType;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.ProfileAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.PublicMessageAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.User1Attribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.Profile;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PublicMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.MessagesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.ProfilesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final ProfilesReceiver profilesReceiver;

    public InfoCommand(CommonReceiver commonReceiver, ProfilesReceiver profilesReceiver) {
        this.commonReceiver = commonReceiver;
        this.profilesReceiver = profilesReceiver;

        numberArgs = 1;
    }

    @Override
    public void execute(String[] args) {
        if (!check(args)) {
            commonReceiver.commandTemplate("[логин или -id идентификатор_пользователя]");
            return;
        }

        boolean findId;
        User1 user1;
        if (args[0].equals("-id")) {
            findId = true;
            if ((args.length < 2) || (args[1] == null)) {
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
            List<ElementAttribute> attributes = new ArrayList<>();
            attributes.add(ProfileAttribute.getAttribute(AttributeType.EQUAL.getID(),
                    ProfileAttribute.PARENT, String.valueOf(user1.getId())));

            List<Profile> profiles = ServiceFactory.getInstance()
                    .getProfileService()
                    .findByAttribute(attributes);

            if ((profiles == null) || (profiles.isEmpty())) {
                commonReceiver.sendError("Профиль пользователя \"" + args[0] + "\" не найден\n" +
                        "Критическая ошибка");
            }
            else {
                profilesReceiver.showProfile(profiles.get(0));
            }
        }
    }
}
