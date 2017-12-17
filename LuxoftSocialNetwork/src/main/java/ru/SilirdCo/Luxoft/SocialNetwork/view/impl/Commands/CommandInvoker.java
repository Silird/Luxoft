package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Commands;

import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PrivateMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Structure;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventType;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.Factories.SenderFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;
import ru.SilirdCo.Luxoft.SocialNetwork.view.interfaces.Commands.ICommand;
import rx.Subscription;

import java.util.Date;

public class CommandInvoker {
    private Subscription subscription = null;

    private final ICommand helpCommand;

    private final ICommand registerCommand;
    private final ICommand showUsersCommand;
    private final ICommand loginCommand;
    private final ICommand logoutCommand;
    private final ICommand selectCommand;
    private final ICommand leaveCommand;

    private final ICommand historyCommand;
    private final ICommand publishCommand;
    private final ICommand historyPublishCommand;

    private final ICommand infoCommand;
    private final ICommand updateProfileCommand;

    public CommandInvoker(ICommand helpCommand,

                          ICommand registerCommand, ICommand showUsersCommand,
                          ICommand loginCommand, ICommand logoutCommand,
                          ICommand selectCommand, ICommand leaveCommand,

                          ICommand historyCommand,
                          ICommand publishCommand, ICommand historyPublishCommand,

                          ICommand infoCommand, ICommand updateProfileCommand) {
        this.helpCommand = helpCommand;

        this.registerCommand = registerCommand;
        this.showUsersCommand = showUsersCommand;
        this.loginCommand = loginCommand;
        this.logoutCommand = logoutCommand;
        this.selectCommand = selectCommand;
        this.leaveCommand = leaveCommand;

        this.historyCommand = historyCommand;
        this.publishCommand = publishCommand;
        this.historyPublishCommand = historyPublishCommand;

        this.infoCommand = infoCommand;
        this.updateProfileCommand = updateProfileCommand;
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

                    boolean message = false;
                    switch (command) {
                        case "0":
                        case "help":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/0":
                        case "/help":
                            helpCommand.execute(args);
                            break;
                        case "10":
                        case "register":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/10":
                        case "/register":
                            registerCommand.execute(args);
                            break;
                        case "11":
                        case "showUsers":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/11":
                        case "/showUsers":
                            showUsersCommand.execute(args);
                            break;
                        case "12":
                        case "login":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/12":
                        case "/login":
                            loginCommand.execute(args);
                            break;
                        case "13":
                        case "logout":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/13":
                        case "/logout":
                            logoutCommand.execute(args);
                            break;
                        case "14":
                        case "select":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/14":
                        case "/select":
                            selectCommand.execute(args);
                            break;
                        case "15":
                        case "leave":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/15":
                        case "/leave":
                            leaveCommand.execute(args);
                            break;
                        case "20":
                        case "history":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/20":
                        case "/history":
                            historyCommand.execute(args);
                            break;
                        case "21":
                        case "publish":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/21":
                        case "/publish":
                            publishCommand.execute(args);
                            break;
                        case "22":
                        case "historyPublish":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/22":
                        case "/historyPublish":
                            historyPublishCommand.execute(args);
                            break;
                        case "30":
                        case "info":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/30":
                        case "/info":
                            infoCommand.execute(args);
                            break;
                        case "31":
                        case "updateProfile":
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                        case "/31":
                        case "/updateProfile":
                            updateProfileCommand.execute(args);
                            break;
                        default:
                            if (StructureView.targetUser.get() != null) {
                                message = true;
                                break;
                            }
                            else {
                                EventSender.sendMessage("Неизвестная команда введите \"/help\" для получения списка команд");
                            }
                    }

                    if (message) {
                        User1 auth = StructureView.authUser.get();
                        User1 target = StructureView.targetUser.get();
                        String login;
                        if (auth != null) {
                            login = auth.getLogin();
                        }
                        else {
                            login = "[???]";
                        }

                        Date date = new Date();
                        EventSender.sendSelfMessage(login + " [" +
                                StructureView.formatDateTime.format(date) + "]: " + event.getText());

                        PrivateMessage newMessage = new PrivateMessage();
                        newMessage.setId(null);
                        newMessage.setMessage(event.getText());
                        newMessage.setRead(false);
                        newMessage.setParent(auth);
                        newMessage.setTarget(target);
                        newMessage.setDate(date);

                        ServiceFactory.getInstance()
                                .getPrivateMessageService()
                                .save(newMessage);
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
