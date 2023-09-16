package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.TetrisApplication;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.BoardAPI;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.TetrominoAPI;
import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.preparing.infrastructure.secondary.spring.TetrominoGeneratedEventDTO;
import com.ippon.kata.tetris.scoring.infrastructure.secondary.spring.ScoreUpdatedEventDTO;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardDTO;
import com.ippon.kata.tetris.shared.secondary.spring.model.LinesErasedEventDTO;
import com.ippon.kata.tetris.shared.secondary.spring.model.TetrominoMovedEventDTO;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class TetrominoGame extends Application {

  private static final int WIDTH = 10;
  private static final int HEIGHT = 22;
  private static final int BLOCK_SIZE = 40;
  public static final String TITLE = "Tetromino";
  public static final URL TETRIS_MP3 = TetrominoGame.class.getResource("/sounds/tetris-theme.mp3");
  public static final int TETROMINO_BLOC_SIZE = 4;
  public static final double PADDING = 20.0;
  public static final int FONT_SIZE = 25;
  private static final URL LINE_ERASED_SOUND_MP3 =
      TetrominoGame.class.getResource("/sounds/erased_line.mp3");
  public static final String TETROMINO_SUIVANT = "Tetromino suivant";
  public static final String SCORE_S = "Score : %s";
  public static final String LEVEL = "Level : %s";
  public static final int TEXT_HEIGHT = 20;
  public static final int TEXT_WIDTH = 300;
  public static final double HALF = 2.0;
  public static final int LEVEL_Y_OFFSET = 60;
  private ConfigurableApplicationContext applicationContext;
  private BoardAPI boardAPI;
  private TetrominoAPI tetrominoAPI;
  private GameId gameId;
  private TetrisGameStartUseCase tetrisGameStartUseCase;
  private MediaPlayer mediaPlayer;

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
    graphicsContext.setFont(new Font(FONT_SIZE));
    initMediaPLayers();
    renderStartButton(graphicsContext);

    startGame();
    renderBoard(graphicsContext);
    playTetrisThemeMusic();

    primaryStage.show();
  }

  private void renderStartButton(GraphicsContext graphicsContext) {

    final double y = yStartButton();

    graphicsContext.setFill(Color.LIGHTGRAY);
    graphicsContext.fillRoundRect(
        xStartButton(), y, widthStartButton(), heightStartButton(), 10, 10);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText("Démarrer", xStartButton() + PADDING, y + 2 * PADDING);
  }

  private static double heightStartButton() {
    return BLOCK_SIZE * 1.5;
  }

  private static double widthStartButton() {
    return BLOCK_SIZE * 3.5;
  }

  private static double yStartButton() {
    return (HEIGHT - 3.0) * BLOCK_SIZE;
  }

  private static int xStartButton() {
    return xOrigin(boardWidth());
  }

  private void initMediaPLayers() throws URISyntaxException {
    Media mediaMusicGame = new Media(TETRIS_MP3.toURI().toString());
    mediaPlayer = new MediaPlayer(mediaMusicGame);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.setAutoPlay(true);
    mediaPlayer.setVolume(0.1);
  }

  private void playTetrisThemeMusic() {
    mediaPlayer.play();
  }

  private void registerListeners(GraphicsContext graphicsContext) {
    applicationContext.addApplicationListener(
        (ApplicationListener<TetrominoMovedEventDTO>)
            event -> Platform.runLater(() -> renderTetrominos(graphicsContext)));
    applicationContext.addApplicationListener(
        (ApplicationListener<ScoreUpdatedEventDTO>)
            event -> Platform.runLater(() -> renderScore(graphicsContext, event)));
    applicationContext.addApplicationListener(
        (ApplicationListener<NextRoundStartedEventDTO>)
            event -> Platform.runLater(() -> renderLevel(graphicsContext, event)));
    applicationContext.addApplicationListener(
        (ApplicationListener<LinesErasedEventDTO>)
            event -> Platform.runLater(this::renderErasedLines));
    applicationContext.addApplicationListener(
        (ApplicationListener<TetrominoGeneratedEventDTO>)
            event -> Platform.runLater(() -> renderNextTetromino(graphicsContext, event)));
  }

  private void renderLevel(GraphicsContext graphicsContext, NextRoundStartedEventDTO event) {
    graphicsContext.setFill(Color.WHITE);
    final int x = WIDTH * BLOCK_SIZE + TEXT_HEIGHT;
    final double y = HEIGHT * BLOCK_SIZE / HALF + LEVEL_Y_OFFSET;
    graphicsContext.fillRect(x, y - TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(LEVEL.formatted(event.level()), x, y);
  }

  private void renderScore(GraphicsContext graphicsContext, ScoreUpdatedEventDTO event) {
    graphicsContext.setFill(Color.WHITE);
    final int x = WIDTH * BLOCK_SIZE + TEXT_HEIGHT;
    final double y = HEIGHT * BLOCK_SIZE / HALF + TEXT_HEIGHT;
    graphicsContext.fillRect(x, y - TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(SCORE_S.formatted(event.score()), x, y);
  }

  private void renderNextTetromino(
      GraphicsContext graphicsContext, TetrominoGeneratedEventDTO event) {
    final int boardWidth = boardWidth();
    final int xOrigin = xOrigin(boardWidth);
    final int yOrigin = 3 * BLOCK_SIZE;
    clearPreviousTetromino(graphicsContext, xOrigin, yOrigin);
    wrapInShape(event.getShape())
        .initPositions()
        .forEach(
            position ->
                renderTetromino(
                    graphicsContext,
                    ShapeType.valueOf(event.getShape()),
                    xOrigin + (position.x() - TETROMINO_BLOC_SIZE) * BLOCK_SIZE,
                    yOrigin + (position.y()) * BLOCK_SIZE));
  }

  private static int xOrigin(int boardWidth) {
    return boardWidth + boardWidth / 2 - BLOCK_SIZE;
  }

  private static int boardWidth() {
    return WIDTH * BLOCK_SIZE;
  }

  private void clearPreviousTetromino(GraphicsContext graphicsContext, int x, int y) {
    final double blockSize = BLOCK_SIZE;
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(TETROMINO_SUIVANT, x - blockSize, y - blockSize - PADDING);
    graphicsContext.strokeRect(
        x - blockSize,
        y - blockSize,
        (1 + TETROMINO_BLOC_SIZE) * blockSize,
        TETROMINO_BLOC_SIZE * blockSize);
    graphicsContext.setFill(Color.LIGHTGRAY);
    graphicsContext.fillRect(
        x - blockSize,
        y - blockSize,
        (1 + TETROMINO_BLOC_SIZE) * blockSize,
        TETROMINO_BLOC_SIZE * blockSize);
  }

  private Shape wrapInShape(String shape) {
    return new Shape(ShapeType.valueOf(shape));
  }

  private void renderErasedLines() {
    final AudioClip audioClip;
    try {
      audioClip = new AudioClip(LINE_ERASED_SOUND_MP3.toURI().toString());
      audioClip.setVolume(5);
      audioClip.play();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private GraphicsContext initCanvas(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root);

    scene.setOnKeyPressed(
        event -> {
          switch (event.getCode()) {
            case DOWN -> tetrominoAPI.move(gameId, Direction.DOWN);
            case LEFT -> tetrominoAPI.move(gameId, Direction.LEFT);
            case RIGHT -> tetrominoAPI.move(gameId, Direction.RIGHT);
            case SPACE -> tetrominoAPI.move(gameId, Direction.ROTATE);
          }
        });
    scene.setOnMouseClicked(this::onMouseClicked);

    final Canvas canvas = new Canvas(HALF * WIDTH * BLOCK_SIZE, (double) HEIGHT * BLOCK_SIZE);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    primaryStage.setTitle(TITLE);
    primaryStage.setScene(scene);
    root.getChildren().add(canvas);
    return gc;
  }

  private void onMouseClicked(MouseEvent mouseEvent) {
    if (inXStartButtonRange(mouseEvent) && inYStartButtonRange(mouseEvent)) {
      startGame();
    }
  }

  private static boolean inYStartButtonRange(MouseEvent mouseEvent) {
    return mouseEvent.getY() > yStartButton()
        && mouseEvent.getY() < yStartButton() + heightStartButton();
  }

  private static boolean inXStartButtonRange(MouseEvent mouseEvent) {
    return mouseEvent.getX() > xStartButton()
        && mouseEvent.getX() < xStartButton() + widthStartButton();
  }

  private void renderTetrominos(GraphicsContext graphicsContext) {
    renderBoard(graphicsContext);
  }

  private void renderBoard(GraphicsContext graphicsContext) {
    if (gameId != null) {
      final BoardDTO board = boardAPI.booard(gameId.value());
      board
          .slots()
          .forEach(
              (key, value) -> {
                if (value.isEmpty()) {
                  renderEmptySlot(graphicsContext, Color.GRAY, key.y(), key.x());
                } else {
                  renderTetromino(
                      graphicsContext,
                      value.get().shape(),
                      key.y() * BLOCK_SIZE,
                      key.x() * BLOCK_SIZE);
                }
              });
    }
  }

  private static void renderEmptySlot(
      GraphicsContext graphicsContext, Color gray, int key, int key1) {
    graphicsContext.setFill(gray);
    graphicsContext.fillRect(
        (double) key * BLOCK_SIZE, (double) key1 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(
        (double) key * BLOCK_SIZE, (double) key1 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
  }

  private static void renderTetromino(
      GraphicsContext graphicsContext, ShapeType tetrominoDTO, int x, int y) {
    setTetrominoFillColor(graphicsContext, tetrominoDTO);

    graphicsContext.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
  }

  private static void setTetrominoFillColor(GraphicsContext graphicsContext, ShapeType shape) {
    graphicsContext.setFill(
        switch (shape) {
          case I -> Color.YELLOW;
          case J -> Color.AQUA;
          case L -> Color.AZURE;
          case O -> Color.SALMON;
          case S -> Color.LIGHTGREEN;
          case T -> Color.PINK;
          case Z -> Color.BLUEVIOLET;
        });
  }

  private void startGame() {
    gameId = null;
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
