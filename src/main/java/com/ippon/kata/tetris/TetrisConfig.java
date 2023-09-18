package com.ippon.kata.tetris;

import com.ippon.kata.tetris.executing.application.domain.BoardInitializedEvent;
import com.ippon.kata.tetris.executing.application.domain.Boards;
import com.ippon.kata.tetris.executing.application.domain.EraseLine;
import com.ippon.kata.tetris.executing.application.domain.FallTetromino;
import com.ippon.kata.tetris.executing.application.domain.InitializeBoard;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import com.ippon.kata.tetris.executing.application.domain.MoveTetromino;
import com.ippon.kata.tetris.executing.application.domain.PickTetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.executing.application.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.application.usecase.EraseLineUseCase;
import com.ippon.kata.tetris.executing.application.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.application.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.application.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.executing.application.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.executing.infrastructure.secondary.spring.TetrominoMovedPublisher;
import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.gaming.application.domain.StartNextRound;
import com.ippon.kata.tetris.gaming.application.domain.TetrisGameStart;
import com.ippon.kata.tetris.gaming.application.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.preparing.application.domain.GenerateNextTetromino;
import com.ippon.kata.tetris.preparing.application.usecase.GenerateNextTetrominoUseCase;
import com.ippon.kata.tetris.preparing.infrastructure.secondary.spring.TetrominoGeneratedPublisher;
import com.ippon.kata.tetris.scoring.application.domain.ComputeScore;
import com.ippon.kata.tetris.scoring.application.domain.InitializeScore;
import com.ippon.kata.tetris.scoring.application.domain.Scores;
import com.ippon.kata.tetris.scoring.application.usecase.ComputeScoreUseCase;
import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TetrisConfig {

  @Bean
  GenerateNextTetrominoUseCase generateNextTetrominoUseCase(
      TetrominoGeneratedPublisher eventPublisher) {

    return new GenerateNextTetromino(eventPublisher);
  }

  @Bean
  InitializeScoreUseCase initializeScoreUseCase(Scores scores) {
    return new InitializeScore(scores);
  }

  @Bean
  InitializeBoardUseCase initializeBoardUseCase(
      Boards boards, EventPublisher<BoardInitializedEvent> eventPublisher) {
    return new InitializeBoard(boards, eventPublisher);
  }

  @Bean
  TetrisGameStartUseCase tetrisGameStartUseCase(
      EventPublisher<GameStartedEvent> eventPublisher, Games games) {
    return new TetrisGameStart(eventPublisher, games);
  }

  @Bean
  StartNextRoundUseCase startNextRoundUseCase(
      Games games, EventPublisher<NextRoundStartedEvent> eventPublisher) {
    return new StartNextRound(games, eventPublisher);
  }

  @Bean
  PickTetrominoUseCase pickTetrominoUseCase(EventPublisher<TetrominoPickedEvent> eventPublisher) {
    return new PickTetromino(eventPublisher);
  }

  @Bean
  FallTetrominoUseCase moveTetrominoOnInitPositionUseCase(
      EventPublisher<TetrominoMovedEvent> eventPublisher, Boards boards) {
    return new FallTetromino(eventPublisher, boards);
  }

  @Bean
  EraseLineUseCase eraseLineUseCase(
      Boards boards, EventPublisher<LinesErasedEvent> eventPublisher) {
    return new EraseLine(boards, eventPublisher);
  }

  @Bean
  MoveTetrominoUseCase moveTetrominoUseCase(
      Boards boards, TetrominoMovedPublisher tetrominoMovedPublisher) {
    return new MoveTetromino(boards, tetrominoMovedPublisher);
  }

  @Bean
  ComputeScoreUseCase updateScoreUseCase(Scores scores) {
    return new ComputeScore(scores);
  }
}
