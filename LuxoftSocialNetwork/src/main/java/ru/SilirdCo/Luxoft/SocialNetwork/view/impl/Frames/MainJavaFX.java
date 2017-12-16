package ru.SilirdCo.Luxoft.SocialNetwork.view.impl.Frames;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainJavaFX extends Application {
    public static MainFrameController mainFrameController;
    public static Node mainFrameNode;

    public static void show(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Frames/MainFrame.fxml"));
        mainFrameController = new MainFrameController();
        loader.setController(mainFrameController);

        Node mainFrameNode = null;
        try {
            mainFrameNode = loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene((Parent) mainFrameNode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Главная форма");
        primaryStage.show();
        //primaryStage.setMaximized(true);

        primaryStage.setOnCloseRequest(we -> {
            Platform.exit();
            System.exit(0);
        });


    }
}
