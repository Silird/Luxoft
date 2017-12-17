package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.MessagesCommands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.AttributeType;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.PrivateMessageAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PrivateMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.MessagesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.util.ArrayList;
import java.util.List;

public class HistoryCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final MessagesReceiver messagesReceiver;

    public HistoryCommand(CommonReceiver commonReceiver, MessagesReceiver messagesReceiver) {
        this.commonReceiver = commonReceiver;
        this.messagesReceiver = messagesReceiver;
    }

    @Override
    public void execute(String[] args) {
        if (StructureView.targetUser.get() == null) {
            commonReceiver.sendWarn("Вы не состоите в диалоге");
            return;
        }

        if (!check(args)) {
            commonReceiver.commandTemplate("");
            return;
        }

        User1 authUser = StructureView.authUser.get();
        User1 targetUser = StructureView.targetUser.get();

        List<ElementAttribute> attributes = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        ids.add(String.valueOf(targetUser.getId()));
        ids.add(String.valueOf(authUser.getId()));

        attributes.add(PrivateMessageAttribute.getAttribute(AttributeType.IN_THE_LIST.getID(),
                PrivateMessageAttribute.PARENT, ids));

        List<PrivateMessage> messages = ServiceFactory.getInstance()
                .getPrivateMessageService()
                .findByAttribute(attributes);

        messagesReceiver.history(messages);
    }
}
