package com.ippon.kata.tetris.gaming.primary.javafx;

import com.ippon.kata.tetris.TetrisApplication;
import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.secondary.spring.model.TetrominoMovedEventDTO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class TetrominoGame extends Application {
  private static final int WIDTH = 10; // the board is 12 cells wide
  private static final int HEIGHT = 22; // ... and 18 cells high
  private static final int BLOCK_SIZE = 20; // block size to render on screen
  public static final String TITLE = "Tetromino";
  private ConfigurableApplicationContext applicationContext;
  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(TetrisApplication.class).run();
  }
  @Override
  public void start(Stage primaryStage) throws Exception {
    GraphicsContext graphicsContext = initCanvas(primaryStage);

    final TetrisGameStartUseCase tetrisGameStartUseCase = applicationContext.getBean(TetrisGameStartUseCase.class);
    startGame(tetrisGameStartUseCase);

    renderBoard(graphicsContext);
    applicationContext.addApplicationListener((ApplicationListener<TetrominoMovedEventDTO>) event -> Platform.runLater(()-> renderTetromino(graphicsContext, event)));

    primaryStage.show();
  }

  private static GraphicsContext initCanvas(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root);
    final Canvas canvas = new Canvas(2 * WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    primaryStage.setTitle(TITLE);
    primaryStage.setScene(scene);
    root.getChildren().add(canvas);
    return gc;
  }

  private void renderTetromino(GraphicsContext graphicsContext, TetrominoMovedEventDTO event) {
    renderBoard(graphicsContext);
    graphicsContext.setFill( switch (event.shapeType()){
      case I -> Color.YELLOW;
      case J -> Color.RED;
      case L -> Color.BLUE;
      case O -> Color.ORANGE;
      case S -> Color.GREEN;
      case T -> Color.PINK;
      case Z -> Color.PURPLE;
    });
    event
        .positions()
        .forEach(
            positionDTO -> {
              System.out.println("Positions:"+positionDTO);
              graphicsContext.fillRect(
                  positionDTO.y() * BLOCK_SIZE,
                  positionDTO.x() * BLOCK_SIZE,
                  BLOCK_SIZE,
                  BLOCK_SIZE);
              graphicsContext.strokeRect(
                  positionDTO.y() * BLOCK_SIZE,
                  positionDTO.x() * BLOCK_SIZE,
                  BLOCK_SIZE,
                  BLOCK_SIZE);
            });
  }

  private static void renderBoard(GraphicsContext graphicsContext) {
    // Render the board
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        graphicsContext.strokeRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
      }
    }
  }

  private static void startGame(TetrisGameStartUseCase tetrisGameStartUseCase) {
    Task<GameStartedEvent> task =
        new Task<>() {
          @Override
          protected GameStartedEvent call() {
            return tetrisGameStartUseCase.start();
          }
        };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
    tetrisGameStartUseCase.start();
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
