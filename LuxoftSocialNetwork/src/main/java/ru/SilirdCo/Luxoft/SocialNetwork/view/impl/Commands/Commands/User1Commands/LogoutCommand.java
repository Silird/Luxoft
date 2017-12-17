package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.User1Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.util.List;

public class LogoutCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;
    private final User1Receiver user1Receiver;

    public LogoutCommand(CommonReceiver commonReceiver, User1Receiver user1Receiver) {
        this.commonReceiver = commonReceiver;
        this.user1Receiver = user1Receiver;
    }

    @Override
    public void execute(String[] args) {
        if (!check(args)) {
            commonReceiver.commandTemplate("");
            return;
        }

        user1Receiver.logout();
    }
}
