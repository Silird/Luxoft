package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;

import java.text.SimpleDateFormat;

public class StructureView {
    public static ObjectProperty<User1> authUser = new SimpleObjectProperty<>(null);
    public static ObjectProperty<User1> targetUser = new SimpleObjectProperty<>(null);

    public final static SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
    public final static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
}
