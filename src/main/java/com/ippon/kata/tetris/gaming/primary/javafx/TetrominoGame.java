package com.ippon.kata.tetris.gaming.primary.javafx;

import com.ippon.kata.tetris.TetrisApplication;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TetrominoGame extends Application {

  public static final String TITLE = "Tetromino";

  @Override
  public void start(Stage primaryStage) throws Exception {
    final URL resource =  TetrisApplication.class.getResource("/gaming/primary/javafx/view/BoardController.fxml");
    FXMLLoader loader = new FXMLLoader(resource);
    AnchorPane page = loader.load();
    Scene scene = new Scene(page);

    primaryStage.setTitle(TITLE);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
