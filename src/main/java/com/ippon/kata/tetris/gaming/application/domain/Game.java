package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.asserts.Asserts;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;

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

  public GameStatus status() {
    return boardInitialized && tetrominoGenerated && scoreInitialized
        ? GameStatus.PLAYING
        : GameStatus.INITIALIZING;
  }

  public Game newRound() {
    final Round nextRound = currentRound.next();
    if (ended) {
      throw new GameEndedException();
    }
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

  public Game tetrominoGenerated(ShapeType shapeType) {
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

  private Settings settings(Round nextRound) {
    return isLevelUp(nextRound) ? settings.increaseLevel() : settings;
  }

  private static boolean isLevelUp(Round round) {
    return round.index() > 0 && round.index() % NB_MOVED_BEFORE_LEVEL_UP == 0;
  }

  public Game finishRound() {
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

  public boolean nextRoundAvailable() {
    return !ended
        && status() == GameStatus.PLAYING
        && currentRound().status() != RoundStatus.STARTED
        && waitingTetromino() != null;
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
}
