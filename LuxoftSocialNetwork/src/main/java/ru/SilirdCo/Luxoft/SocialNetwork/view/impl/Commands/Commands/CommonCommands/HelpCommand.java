package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.CommonCommands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands.BaseCommand;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Receivers.CommonReceiver;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;
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

        String result;
        if (StructureView.targetUser.get() == null) {
            result = "Команды можно вводить с помощью строкового или числового идентификатора:\n";
        }
        else {
            result = "Введите сообщение чтобы отправить его или введите  команду через \"/\"\n" +
                    "Команды можно вводить с помощью строкового или числового идентификатора:\n";
        }

        result += "/help [0]\n" +
                "/register [10]\n" +
                "/showUsers [11]\n" +
                "/login [12]\n" +
                "/logout [13]\n" +
                "/select [14]\n" +
                "/leave [15]\n" +
                "/history [20]\n" +
                "/publish [21]\n" +
                "/historyPublish [22]\n" +
                "/info [30]\n" +
                "/updateProfile [31]\n";

        commonReceiver.sendMessage(result);
    }
}
