package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Frames;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.AttributeType;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.ElementAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Attributes.Generated.PrivateMessageAttribute;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.PrivateMessage;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Entities.Generated.User1;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.Factories.Services.ServiceFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventType;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.Factories.SenderFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.VarUtils;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.StructureView;

import java.net.URL;
import java.util.*;

public class MainFrameController implements Initializable {
    @FXML
    private Label labelUser;
    @FXML
    private Label labelTarget;

    @FXML
    private VBox logArea;
    @FXML
    ScrollPane scroll;
    TextFlow textFlow;

    @FXML
    private TextField textCommand;

    @FXML
    private Button butSend;

    private static Text getDefaultText(String str) {
        Text text = new Text(str + "\n");
        text.setFill(Color.color(0, 0, 0));
        //text.setFont(new Font("Arial", 12));
        return text;
    }

    private static Text messageStyle(String str) {
        return getDefaultText(str);
    }
    private static Text selfMessageStyle(String str) {
        Text text = getDefaultText(str);
        text.setFill(Color.color(0, 0, 1));
        return text;
    }

    private boolean needScroll = true;

    private Timer timer = null;

    public void initialize(URL location, ResourceBundle resources) {
        initLog();
        initListeners();
        initLabels();
    }

    private void initLog() {
        textFlow = new TextFlow();
        logArea.getChildren().add(textFlow);

        logArea.heightProperty().addListener(((observable, oldValue, newValue) -> {
            if (needScroll) {
                logArea.layout();
                scroll.setVvalue(1.0d);
                needScroll = false;
            }
        }));

        SenderFactory.getInstance()
                .getEvents()
                .getObservable()
                .filter(event -> ((event.getType() == EventType.MESSAGE) || (event.getType() == EventType.SELF_MESSAGE)))
                .subscribe(event -> {
                    Platform.runLater(() -> {
                        Text text1;
                        if (event.getType() == EventType.SELF_MESSAGE) {
                            text1 = selfMessageStyle(event.getText());
                        }
                        else {
                            text1 = messageStyle(event.getText());
                        }

                        if (scroll.getVvalue() == 1d) {
                            needScroll = true;
                        }

                        textFlow.getChildren().add(text1);
                    });
                });
    }

    private void initListeners() {
        butSend.setOnAction(event -> sendCommand());
        textCommand.setOnAction(event -> sendCommand());
    }

    private void initLabels() {
        labelUser.setText("Вы: неавторизированы");
        StructureView.authUser.setValue(null);
        StructureView.authUser.addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) {
                labelUser.setText("Вы: неавторизированы");
            }
            else {
                labelUser.setText("Вы: " + newValue.getLogin() + "[" + newValue.getId() + "]");
            }
        }));

        labelTarget.setText("Командный режим");
        StructureView.targetUser.setValue(null);
        StructureView.targetUser.addListener(((observable, oldValue, newValue) -> {
            if (timer != null) {
                timer.cancel();
            }
            if (newValue == null) {
                timer = null;
                labelTarget.setText("Командный режим");
            }
            else {
                labelTarget.setText("Диалог: " + newValue.getLogin() + "[" + newValue.getId() + "]");
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //EventSender.sendMessage("Проверка");

                        User1 authUser = StructureView.authUser.get();
                        if (authUser != null) {
                            List<ElementAttribute> attributes = new ArrayList<>();
                            attributes.add(PrivateMessageAttribute.getAttribute(AttributeType.EQUAL.getID(),
                                    PrivateMessageAttribute.TARGET, String.valueOf(authUser.getId())));
                            attributes.add(PrivateMessageAttribute.getAttribute(AttributeType.EQUAL.getID(),
                                    PrivateMessageAttribute.PARENT, String.valueOf(newValue.getId())));
                            attributes.add(PrivateMessageAttribute.getAttribute(AttributeType.EQUAL.getID(),
                                    PrivateMessageAttribute.READ, String.valueOf(false)));

                            List<PrivateMessage> messages = ServiceFactory.getInstance()
                                    .getPrivateMessageService()
                                    .findByAttribute(attributes);

                            for (PrivateMessage message : messages) {
                                EventSender.sendMessage(newValue.getLogin() + ": " + message.getMessage());
                                message.setRead(true);
                                ServiceFactory.getInstance()
                                        .getPrivateMessageService()
                                        .save(message);
                            }
                        }
                    }
                };

                timer = new Timer();
                timer.schedule(timerTask, 0, 1000);
            }
        }));
    }

    private void sendCommand() {
        String strCommand = VarUtils.getString(textCommand.getText());
        textCommand.setText("");
        EventSender.sendCommand(strCommand);
    }
}
