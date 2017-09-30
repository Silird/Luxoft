package ru.SilirdCo.Luxoft.impl.Commands;

public class Invoker {
    private Commands commands;

    public void setCommands(Commands commands) {
        this.commands = commands;
    }

    public void run() {
        commands.execute();
    }
}
