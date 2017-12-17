package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.ProfilesCommands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.AttributeType;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.ProfileAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.Profile;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.ProfilesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateProfileCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final ProfilesReceiver profilesReceiver;

    public UpdateProfileCommand(CommonReceiver commonReceiver, ProfilesReceiver profilesReceiver) {
        this.commonReceiver = commonReceiver;
        this.profilesReceiver = profilesReceiver;
    }

    @Override
    public void execute(String[] args) {
        if (StructureView.authUser.get() == null) {
            commonReceiver.sendWarn("Для использования данной команды требуется авторизация (\"/login\")");
            return;
        }

        if (!check(args)) {
            commonReceiver.commandTemplate("");
            return;
        }

        if (args.length%2 != 0) {
            commonReceiver.commandTemplate("[(-n имя) (-l фамилия) (-c страна) (-d дата_рождения(dd.MM.yyyy))]");
            return;
        }

        Profile profile;

        List<ElementAttribute> attributes = new ArrayList<>();
        attributes.add(ProfileAttribute.getAttribute(AttributeType.EQUAL.getID(),
                ProfileAttribute.PARENT, String.valueOf(StructureView.authUser.get().getId())));

        List<Profile> profiles = ServiceFactory.getInstance()
                .getProfileService()
                .findByAttribute(attributes);

        if ((profiles == null) || (profiles.isEmpty())) {
            profile = new Profile();
            profile.setId(null);
            profile.setName(null);
            profile.setLastName(null);
            profile.setBirthDay(null);
            profile.setCountry(null);
            profile.setParent(StructureView.authUser.get());
        }
        else {
            profile = profiles.get(0);
        }

        String name = profile.getName();
        String lastName = profile.getLastName();
        String country = profile.getCountry();
        Date birthDay = profile.getBirthDay();

        int pair = 2;
        while (args.length >= pair) {
            switch (args[pair - 2]) {
                case "-n":
                    name = args[pair - 1];
                    break;
                case "-l":
                    lastName = args[pair - 1];
                    break;
                case "-c":
                    country = args[pair - 1];
                    break;
                case "-d":
                    try {
                        birthDay = StructureView.formatDate.parse(args[pair - 1]);
                    }
                    catch (ParseException ex) {
                        commonReceiver.sendWarn("Неверно задана дата, формат: dd.MM.yyyy");
                    }
                    break;
                default:
                    commonReceiver.sendWarn("Неизвестный идентификатор: " + args[pair - 2]);
                    commonReceiver.commandTemplate("[(-n имя) (-l фамилия) (-c страна) (-d дата_рождения(dd.MM.yyyy))]");
                    return;
            }

            pair += 2;
        }

        profile.setName(name);
        profile.setLastName(lastName);
        profile.setCountry(country);
        profile.setBirthDay(birthDay);

        profile = ServiceFactory.getInstance()
                .getProfileService()
                .save(profile);

        profilesReceiver.updateProfile(profile);
    }
}
