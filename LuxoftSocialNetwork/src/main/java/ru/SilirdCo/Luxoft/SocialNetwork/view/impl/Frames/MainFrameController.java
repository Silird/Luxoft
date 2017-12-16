package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Frames;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.Event;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventSender;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Events.EventType;
import ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Util.Factories.SenderFactory;
import ru.SilirdCo.Luxoft.SocialNetwork.core.impl.Util.VarUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable {
    @FXML
    TextArea textLog;

    @FXML
    TextField textCommand;

    @FXML
    Button butSend;

    public void initialize(URL location, ResourceBundle resources) {
        SenderFactory.getInstance()
                .getEvents()
                .getObservable()
                .filter(event -> event.getType() == EventType.MESSAGE)
                .subscribe(event -> {
                    textLog.setText(textLog.getText() + event.getText() + "\n");
                    textLog.setScrollTop(Double.MAX_VALUE);
                });

        butSend.setOnAction(event -> sendCommand());
        textCommand.setOnAction(event -> sendCommand());
    }

    private void sendCommand() {
        String strCommand = VarUtils.getString(textCommand.getText());
        textCommand.setText("");
        EventSender.sendCommand(strCommand);
    }
}
