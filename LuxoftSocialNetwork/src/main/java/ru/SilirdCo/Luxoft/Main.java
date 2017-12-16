package ru.SilirdCo.Luxoft;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands.CommandService;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Frames.MainJavaFX;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
        new CommandService();

        MainJavaFX.show(args);
    }
}
