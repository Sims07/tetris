package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.TetrisApplication;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.BoardAPI;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.TetrominoAPI;
import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardDTO;
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
  private static final int BLOCK_SIZE = 40; // block size to render on screen
  public static final String TITLE = "Tetromino";
  private ConfigurableApplicationContext applicationContext;
  private BoardAPI boardAPI;
  private TetrominoAPI tetrominoAPI;
  private GameId gameId;
  private TetrisGameStartUseCase tetrisGameStartUseCase;

  @Override
  public void init() {
    applicationContext = new SpringApplicationBuilder(TetrisApplication.class).run();
    boardAPI = applicationContext.getBean(BoardAPI.class);
    tetrisGameStartUseCase = applicationContext.getBean(TetrisGameStartUseCase.class);
    tetrominoAPI = applicationContext.getBean(TetrominoAPI.class);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    GraphicsContext graphicsContext = initCanvas(primaryStage);
    registerListeners(graphicsContext);

    startGame(tetrisGameStartUseCase);
    renderBoard(graphicsContext);
    
    primaryStage.show();
  }

  private void registerListeners(GraphicsContext graphicsContext) {
      applicationContext.addApplicationListener(
          (ApplicationListener<TetrominoMovedEventDTO>)
              event -> Platform.runLater(() -> renderTetromino(graphicsContext, event)));
  }

  private GraphicsContext initCanvas(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root);

    scene.setOnKeyPressed(event -> {
      switch (event.getCode()) {
          case DOWN ->  tetrominoAPI.move(gameId, Direction.DOWN);
          case LEFT ->  tetrominoAPI.move(gameId, Direction.LEFT);
          case RIGHT ->  tetrominoAPI.move(gameId, Direction.RIGHT);
      }
    });

    final Canvas canvas = new Canvas(2 * WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    primaryStage.setTitle(TITLE);
    primaryStage.setScene(scene);
    root.getChildren().add(canvas);
    return gc;
  }

  private void renderTetromino(GraphicsContext graphicsContext, TetrominoMovedEventDTO event) {
    renderBoard(graphicsContext);
    setTetrominoFillColor(graphicsContext, event.shapeType());
    event
        .positions()
        .forEach(
            positionDTO -> {
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

  private void renderBoard(GraphicsContext graphicsContext) {
    renderEmptyBoard(graphicsContext);
    if (gameId != null) {
      final BoardDTO board = boardAPI.booard(gameId.value());
      board
          .slots()
          .forEach(
              (key, value) ->
                  value.ifPresent(
                      tetrominoDTO -> {
                        setTetrominoFillColor(graphicsContext, tetrominoDTO.shape());
                        tetrominoDTO
                            .positions()
                            .forEach(
                                positionDTO -> {
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
                      }));
    }
  }

  private static void setTetrominoFillColor(GraphicsContext graphicsContext, ShapeType shape) {
    graphicsContext.setFill(
        switch (shape) {
          case I -> Color.YELLOW;
          case J -> Color.RED;
          case L -> Color.BLUE;
          case O -> Color.ORANGE;
          case S -> Color.GREEN;
          case T -> Color.PINK;
          case Z -> Color.PURPLE;
        });
  }

  private static void renderEmptyBoard(GraphicsContext graphicsContext) {
    for (int i = 0; i < WIDTH; i++) {
      for (int j = 0; j < HEIGHT; j++) {
        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        graphicsContext.strokeRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
      }
    }
  }

  private void startGame(TetrisGameStartUseCase tetrisGameStartUseCase) {
    Task<GameStartedEvent> task =
        new Task<>() {
          @Override
          protected GameStartedEvent call() {
            final GameStartedEvent gameStartedEvent = tetrisGameStartUseCase.start();
            gameId = gameStartedEvent.gameId();
            return gameStartedEvent;
          }
        };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }
}
