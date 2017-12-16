package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;

public abstract class BaseCommand implements ICommand {
    protected int numberArgs = 0;

    protected boolean check(String[] args) {
        if (args == null) return false;
        if (args.length < numberArgs) return false;

        for (int i = 0; i < numberArgs; i++) {
            if (args[i] == null) return false;
        }

        return true;
    }
}
