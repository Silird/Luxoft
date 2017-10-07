package ru.SilirdCo.Luxoft.impl.Commands;

import ru.SilirdCo.Luxoft.impl.Commands.Commands.CreateNewUserCommand;
import ru.SilirdCo.Luxoft.impl.Entities.Network;
import ru.SilirdCo.Luxoft.impl.Messages.Event;
import ru.SilirdCo.Luxoft.impl.Messages.EventSender;
import ru.SilirdCo.Luxoft.impl.Util.Factories.SenderFactory;
import ru.SilirdCo.Luxoft.interfaces.Commands.ICommand;

public class CommandService {
    public CommandService() {
        Receiver receiver = new Receiver(new Network());
        ICommand createNewUserCommand = new CreateNewUserCommand(receiver);

        Invoker invoker = new Invoker();

        SenderFactory.getInstance()
                .getEvents()
                .getObservable()
                .filter(event -> event.getType() == Event.COMMAND)
                .subscribe(event -> {
                    String text = event.getText().replaceAll("[\\s]{2,}", " ");
                    String[] strings = text.split(" ");
                    String command = strings[0];
                    int argsNumber = strings.length - 1;
                    String[] args = new String[argsNumber];
                    System.arraycopy(strings, 1, args, 0, argsNumber);

                    switch (command) {
                        case "0":
                        case "createUser":
                            invoker.setCommand(createNewUserCommand);
                            invoker.run();
                            break;
                        default:
                            EventSender.sendMessage("Неизвестная команда");
                            //throw new IllegalArgumentException("");
                    }
                });
    }
}
