package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.MessagesCommands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.AttributeType;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.User1Attribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PublicMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.MessagesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final MessagesReceiver messagesReceiver;

    public PublishCommand(CommonReceiver commonReceiver, MessagesReceiver messagesReceiver) {
        this.commonReceiver = commonReceiver;
        this.messagesReceiver = messagesReceiver;

        numberArgs = 1;
    }

    @Override
    public void execute(String[] args) {
        if (StructureView.authUser.get() == null) {
            commonReceiver.sendWarn("Для использования данной команды требуется авторизация (\"/login\")");
            return;
        }

        if (!check(args)) {
            commonReceiver.commandTemplate("сообщение");
            return;
        }

        PublicMessage message = new PublicMessage();
        message.setId(null);
        message.setParent(StructureView.authUser.get());
        message.setMessage(args[0]);
        message.setDate(new Date());

        ServiceFactory.getInstance()
                .getPublicMessageService()
                .save(message);

        messagesReceiver.publish(args[0]);
    }
}
