package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.CommonCommands.HelpCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.MessagesCommands.HistoryCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.MessagesCommands.HistoryPublishCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.MessagesCommands.PublishCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.ProfilesCommands.InfoCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.ProfilesCommands.UpdateProfileCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.User1Commands.*;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.MessagesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.ProfilesReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;

public class CommandService {
    private static CommandInvoker invoker = null;

    private static CommonReceiver commonReceiver = new CommonReceiver();
    private static User1Receiver user1Receiver = new User1Receiver();
    private static MessagesReceiver messagesReceiver = new MessagesReceiver();
    private static ProfilesReceiver profilesReceiver = new ProfilesReceiver();

    public static void start() {
        if (invoker != null) {
            stop();
        }

        invoker = new CommandInvoker(new HelpCommand(commonReceiver),

                new RegisterCommand(commonReceiver, user1Receiver),
                new ShowUsersCommand(commonReceiver, user1Receiver),
                new LoginCommand(commonReceiver, user1Receiver),
                new LogoutCommand(commonReceiver, user1Receiver),
                new SelectCommand(commonReceiver, user1Receiver),
                new LeaveCommand(commonReceiver, user1Receiver),

                new HistoryCommand(commonReceiver, messagesReceiver),
                new PublishCommand(commonReceiver, messagesReceiver),
                new HistoryPublishCommand(commonReceiver, messagesReceiver),

                new InfoCommand(commonReceiver, profilesReceiver),
                new UpdateProfileCommand(commonReceiver, profilesReceiver));
        invoker.start();
    }

    public static void stop() {
        if (invoker != null) {
            invoker.stop();
        }
    }
}
