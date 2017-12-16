package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventType;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.Factories.SenderFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;
import rx.Subscription;

public class CommandInvoker {
    private Subscription subscription = null;

    private final ICommand helpCommand;

    private final ICommand createNewUserCommand;
    private final ICommand showUsersCommand;

    public CommandInvoker(ICommand helpCommand,
                          ICommand createNewUserCommand, ICommand showUsersCommand) {
        this.helpCommand = helpCommand;

        this.createNewUserCommand = createNewUserCommand;
        this.showUsersCommand = showUsersCommand;
    }

    public void start() {
        subscription = SenderFactory.getInstance()
                .getEvents()
                .getObservable()
                .filter(event -> event.getType() == EventType.COMMAND)
                .subscribe(event -> {
                    String text = event.getText().replaceAll("[\\s]{2,}", " ");
                    String[] strings = text.split(" ");
                    String command = strings[0];
                    int argsNumber = strings.length - 1;
                    String[] args = new String[argsNumber];
                    System.arraycopy(strings, 1, args, 0, argsNumber);

                    switch (command) {
                        case "0":
                        case "help":
                            helpCommand.execute(args);
                            break;
                        case "10":
                        case "createUser":
                            createNewUserCommand.execute(args);
                            break;
                        case "11":
                        case "showUsers":
                            showUsersCommand.execute(args);
                            break;
                        default:
                            EventSender.sendMessage("Неизвестная команда введите \"help\" для получения списка команд");
                    }
                });
    }

    public void stop() {
        if ((subscription != null) && (!subscription.isUnsubscribed())) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}
