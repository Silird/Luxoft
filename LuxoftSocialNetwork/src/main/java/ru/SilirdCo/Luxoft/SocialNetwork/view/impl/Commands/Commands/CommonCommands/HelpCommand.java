package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.CommonCommands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

import java.util.List;

public class HelpCommand extends BaseCommand implements ICommand {
    private final CommonReceiver commonReceiver;

    public HelpCommand(CommonReceiver commonReceiver) {
        this.commonReceiver = commonReceiver;
    }

    @Override
    public void execute(String[] args) {
        if (!check(args)) {
            commonReceiver.commandTemplate("");
            return;
        }

        String stringBuilder = "Команды можно вводить с помощью строкового или числового идентификатора:\n" +
                "help [0]\n" +
                "createUser [10]\n" +
                "showUsers [11]\n";

        commonReceiver.sendMessage(stringBuilder);
    }
}
