package ru.SilirdCo.Luxoft.impl.Frames;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.SilirdCo.Luxoft.impl.Commands.Commands;
import ru.SilirdCo.Luxoft.impl.Commands.Invoker;
import ru.SilirdCo.Luxoft.impl.Commands.Receiver;
import ru.SilirdCo.Luxoft.impl.Util.VarUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class MainFrameController implements Initializable {
    @FXML
    TextArea textLog;

    @FXML
    TextField textCommand;

    @FXML
    Button butSend;

    public void initialize(URL location, ResourceBundle resources) {
        /*
        CompletableFuture futureIn = CompletableFuture.runAsync(() -> {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.out));
            try {
                String line = in.readLine();
                while (line != null) {
                    //System.out.println(line);
                    textLog.setText(textLog.getText() + "\n" +line);
                    try {
                        line = in.readLine();
                    }
                    catch (IOException ex) {
                    }
                }
                in.close();
            }
            catch (IOException ex) {
                System.exit(1);
            }
        });
        */



        Receiver receiver = new Receiver();
        Commands commands = new Commands();

        Invoker invoker = new Invoker();
        invoker.setCommands(commands);

        butSend.setOnAction(event -> {
            String strCommand = VarUtils.getString(textCommand.getText());
            textCommand.setText("");
            System.out.println(strCommand);
        });
    }
}
