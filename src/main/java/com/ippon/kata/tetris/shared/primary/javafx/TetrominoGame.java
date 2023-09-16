package com.ippon.kata.tetris.shared.primary.javafx;

import static com.ippon.kata.tetris.gaming.infrastructure.primary.javafx.StartButtonRenderer.inXStartButtonRange;
import static com.ippon.kata.tetris.gaming.infrastructure.primary.javafx.StartButtonRenderer.inYStartButtonRange;

import com.ippon.kata.tetris.TetrisApplication;
import com.ippon.kata.tetris.executing.infrastructure.primary.javafx.BoardRenderer;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.BoardAPI;
import com.ippon.kata.tetris.executing.infrastructure.primary.spring.TetrominoAPI;
import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.gaming.infrastructure.primary.javafx.LevelRenderer;
import com.ippon.kata.tetris.gaming.infrastructure.primary.javafx.StartButtonRenderer;
import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.preparing.infrastructure.primary.javafx.NextTetrominoRenderer;
import com.ippon.kata.tetris.preparing.infrastructure.secondary.spring.TetrominoGeneratedEventDTO;
import com.ippon.kata.tetris.scoring.infrastructure.primary.javafx.ScoreRenderer;
import com.ippon.kata.tetris.scoring.infrastructure.secondary.spring.ScoreUpdatedEventDTO;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
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
  public static final int FONT_SIZE = 25;
  private static final URL LINE_ERASED_SOUND_MP3 =
      TetrominoGame.class.getResource("/sounds/erased_line.mp3");
  public static final double HALF = 2.0;
  private ConfigurableApplicationContext applicationContext;
  private BoardAPI boardAPI;
  private TetrominoAPI tetrominoAPI;
  private GameId gameId;
  private TetrisGameStartUseCase tetrisGameStartUseCase;
  private MediaPlayer mediaPlayer;
  private final LevelRenderer levelRenderer;
  private final ScoreRenderer scoreRenderer;
  private final BoardRenderer boardRenderer;
  private final NextTetrominoRenderer nextTetrominoRenderer;
  private final StartButtonRenderer startButtonRenderer;

  public TetrominoGame() {
    levelRenderer = new LevelRenderer();
    scoreRenderer = new ScoreRenderer();
    boardRenderer = new BoardRenderer();
    nextTetrominoRenderer = new NextTetrominoRenderer();
    startButtonRenderer = new StartButtonRenderer();
  }

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
    startButtonRenderer.render(graphicsContext, null);

    startGame();
    renderBoard(graphicsContext);
    playTetrisThemeMusic();

    primaryStage.show();
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
            event -> Platform.runLater(() -> renderBoard(graphicsContext)));
    applicationContext.addApplicationListener(
        (ApplicationListener<ScoreUpdatedEventDTO>)
            event -> Platform.runLater(() -> scoreRenderer.render(graphicsContext, event)));
    applicationContext.addApplicationListener(
        (ApplicationListener<NextRoundStartedEventDTO>)
            event -> Platform.runLater(() -> levelRenderer.render(graphicsContext, event)));
    applicationContext.addApplicationListener(
        (ApplicationListener<LinesErasedEventDTO>)
            event -> Platform.runLater(this::renderErasedLines));
    applicationContext.addApplicationListener(
        (ApplicationListener<TetrominoGeneratedEventDTO>)
            event -> Platform.runLater(() -> nextTetrominoRenderer.render(graphicsContext, event)));
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

  private void renderBoard(GraphicsContext graphicsContext) {
    if (gameId != null) {
      boardRenderer.render(graphicsContext, boardAPI.booard(gameId.value()));
    }
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
