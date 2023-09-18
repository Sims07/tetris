package com.ippon.kata.tetris.gaming.application.domain;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.IDLE;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.domain.asserts.Asserts;
import java.util.UUID;

public record Game(
    GameId id,
    boolean boardInitialized,
    boolean tetrominoGenerated,
    Round currentRound,
    boolean scoreInitialized,
    Tetromino waitingTetromino,
    Settings settings,
    boolean ended) {

  public static final int NB_MOVED_BEFORE_LEVEL_UP = 10;

  public Game {
    Asserts.withContext(getClass()).notNull(id, "Game id should not be null");
  }

  public static Game newGame() {
    return new Game(
        new GameId(UUID.randomUUID()),
        false,
        false,
        new Round(IDLE, 0),
        false,
        null,
        new Settings(new Level(1)),
        false);
  }

  public GameStatus status() {
    return boardInitialized && tetrominoGenerated && scoreInitialized
        ? GameStatus.PLAYING
        : GameStatus.INITIALIZING;
  }

  public Game newRound() {
    final Round nextRound = currentRound.next();
    assertNotEndedGame();
    return new Game(
        id,
        boardInitialized,
        tetrominoGenerated,
        nextRound,
        scoreInitialized,
        null,
        settings(currentRound),
        ended());
  }

  public Game finishRound() {
    assertNotEndedGame();
    return new Game(
        id,
        boardInitialized,
        tetrominoGenerated,
        new Round(RoundStatus.FINISHED, currentRound.index()),
        scoreInitialized,
        waitingTetromino,
        settings,
        ended);
  }

  public Game tetrominoGenerated(ShapeType shapeType) {
    assertNotEndedGame();
    return new Game(
        id,
        boardInitialized,
        true,
        currentRound,
        scoreInitialized,
        new Tetromino(shapeType),
        settings,
        ended);
  }

  public Game end() {
    return new Game(
        id,
        boardInitialized,
        tetrominoGenerated,
        currentRound,
        scoreInitialized,
        waitingTetromino,
        settings,
        true);
  }

  public Game initializeScore() {
    return new Game(
        id,
        boardInitialized,
        tetrominoGenerated,
        currentRound,
        true,
        waitingTetromino,
        settings,
        ended);
  }

  private Settings settings(Round nextRound) {
    return isLevelUp(nextRound) ? settings.increaseLevel() : settings;
  }

  private static boolean isLevelUp(Round round) {
    return round.index() > 0 && round.index() % NB_MOVED_BEFORE_LEVEL_UP == 0;
  }

  public boolean nextRoundAvailable() {
    return !ended
        && status() == GameStatus.PLAYING
        && currentRound().status() != RoundStatus.STARTED
        && waitingTetromino() != null;
  }

  private void assertNotEndedGame() {
    if (ended) {
      throw new GameEndedException();
    }
  }

  public Game initializeBoard() {
    return new Game(
        id(),
        true,
        tetrominoGenerated(),
        currentRound(),
        scoreInitialized(),
        waitingTetromino(),
        settings(),
        false);
  }
}
