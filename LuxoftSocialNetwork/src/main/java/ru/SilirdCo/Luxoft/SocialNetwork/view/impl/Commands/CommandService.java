package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.CommonCommands.HelpCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.User1Commands.CreateNewUserCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.User1Commands.ShowUsersCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.User1Receiver;

public class CommandService {
    private static CommandInvoker invoker = null;

    private static CommonReceiver commonReceiver = new CommonReceiver();
    private static User1Receiver user1Receiver = new User1Receiver();

    public static void start() {
        if (invoker != null) {
            stop();
        }

        invoker = new CommandInvoker(new HelpCommand(commonReceiver),

                new CreateNewUserCommand(commonReceiver, user1Receiver),
                new ShowUsersCommand(commonReceiver, user1Receiver));
        invoker.start();
    }

    public static void stop() {
        if (invoker != null) {
            invoker.stop();
        }
    }
}
