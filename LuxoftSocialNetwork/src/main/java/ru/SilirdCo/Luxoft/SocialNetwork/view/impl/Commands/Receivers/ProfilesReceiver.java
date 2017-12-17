package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.Profile;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.VarUtils;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;

public class ProfilesReceiver {
    public void showProfile(Profile profile) {
        String profileStr = "Профиль пользователя " + profile.getParent().getLogin() + ":\n" +
                "Имя - " + VarUtils.getString(profile.getName()) + "\n" +
                "Фамилия - " + VarUtils.getString(profile.getLastName()) + "\n" +
                "Страна - " + VarUtils.getString(profile.getCountry()) + "\n" +
                "Дата рождения - " + ((profile.getBirthDay() == null) ? "[Не указана]" : StructureView.formatDate.format(profile.getBirthDay())) + "\n";

        EventSender.sendMessage(profileStr);
    }

    public void updateProfile(Profile profile) {
        String profileStr = "Профиль изменён:\n" +
                "Имя - " + profile.getName() + "\n" +
                "Фамилия - " + profile.getLastName() + "\n" +
                "Страна - " + profile.getCountry() + "\n" +
                "Дата рождения - " + ((profile.getBirthDay() == null) ? "[Не указана]" : StructureView.formatDate.format(profile.getBirthDay())) + "\n";

        EventSender.sendMessage(profileStr);
    }
}
