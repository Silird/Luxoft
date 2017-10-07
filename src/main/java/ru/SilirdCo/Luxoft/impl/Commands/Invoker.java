package ru.SilirdCo.Luxoft.impl.Commands;

import ru.SilirdCo.Luxoft.impl.Commands.Commands.CreateNewUserCommand;
import ru.SilirdCo.Luxoft.interfaces.Commands.ICommand;

public class Invoker {
    private ICommand command;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void run() {
        command.execute();
    }
}
