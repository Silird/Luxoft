package ru.SilirdCo.Luxoft;

import ru.SilirdCo.Luxoft.impl.Commands.CommandService;
import ru.SilirdCo.Luxoft.impl.Frames.MainJavaFX;

/**
 * Created by user on 23.09.2017.
 */
public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
        new CommandService();

        MainJavaFX.show(args);
    }
}
