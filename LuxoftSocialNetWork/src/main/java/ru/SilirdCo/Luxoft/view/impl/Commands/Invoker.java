package ru.SilirdCo.Luxoft.view.impl.Commands;

import ru.SilirdCo.Luxoft.view.interfaces.Commands.ICommand;

public class Invoker {
    private ICommand command;

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void run() {
        command.execute();
    }
}
