package com.ippon.kata.tetris.gaming.domain;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.STARTED;
import static com.ippon.kata.tetris.gaming.domain.GameFixture.game;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.GameEndedException;
import com.ippon.kata.tetris.gaming.application.domain.GameStatus;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void givenBoardNotInit_status_shouldReturnInitializing() {
    final Game game = game(false, false, false);

    then(game.status()).isEqualTo(GameStatus.INITIALIZING);
  }

  @Test
  void givenBoardInitTetrominoNotGenerated_status_shouldReturnInitializing() {
    final Game game = game(true, false, false);

    then(game.status()).isEqualTo(GameStatus.INITIALIZING);
  }

  @Test
  void givenBoardInitTetrominoGenerated_status_shouldReturnPlaying() {
    final Game game = game(true, true, false);

    then(game.status()).isEqualTo(GameStatus.PLAYING);
  }

  @Test
  void givenGame_newRound_setANewIdleRound() {
    final Game game = game(true, true, false);

    final Game game1 = game.newRound();

    then(game1.currentRound().status()).isEqualTo(STARTED);
  }

  @Test
  void givenEndedGame_newRound_shouldThrowException() {
    final Game game = game(true, true, true);

    thenThrownBy(game::newRound).isInstanceOf(GameEndedException.class);
  }
}
