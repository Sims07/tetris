package com.ippon.kata.tetris;

import com.ippon.kata.tetris.executing.domain.BoardInitializedEvent;
import com.ippon.kata.tetris.executing.domain.Boards;
import com.ippon.kata.tetris.executing.domain.FallTetromino;
import com.ippon.kata.tetris.executing.domain.InitializeBoard;
import com.ippon.kata.tetris.executing.domain.PickTetromino;
import com.ippon.kata.tetris.executing.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.executing.domain.TetrominoPickedEvent;
import com.ippon.kata.tetris.executing.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.executing.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.executing.usecase.PickTetrominoUseCase;
import com.ippon.kata.tetris.gaming.Games;
import com.ippon.kata.tetris.gaming.TetrisGameStart;
import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.domain.NextRoundStartedEvent;
import com.ippon.kata.tetris.gaming.domain.StartNextRound;
import com.ippon.kata.tetris.gaming.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.preparing.domain.GenerateNextTetromino;
import com.ippon.kata.tetris.preparing.domain.GenerateNextTetrominoUseCase;
import com.ippon.kata.tetris.preparing.secondary.spring.TetrominoGeneratedPublisher;
import com.ippon.kata.tetris.scoring.domain.InitializeScore;
import com.ippon.kata.tetris.scoring.domain.ScoreInitializedEvent;
import com.ippon.kata.tetris.scoring.domain.Scores;
import com.ippon.kata.tetris.scoring.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.EventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TetrisConfig {

    @Bean
    GenerateNextTetrominoUseCase generateNextTetrominoUseCase(TetrominoGeneratedPublisher eventPublisher) {

        return new GenerateNextTetromino(eventPublisher);
    }

    @Bean
    InitializeScoreUseCase initializeScoreUseCase(Scores scores, EventPublisher<ScoreInitializedEvent> eventPublisher) {
        return new InitializeScore(scores, eventPublisher);
    }

    @Bean
    InitializeBoardUseCase initializeBoardUseCase(Boards boards, EventPublisher<BoardInitializedEvent> eventPublisher) {
        return new InitializeBoard(boards, eventPublisher);
    }

    @Bean
    TetrisGameStartUseCase tetrisGameStartUseCase(EventPublisher<GameStartedEvent> eventPublisher, Games games) {
        return new TetrisGameStart(eventPublisher, games);
    }

    @Bean
    StartNextRoundUseCase startNextRoundUseCase(Games games, EventPublisher<NextRoundStartedEvent> eventPublisher) {
        return new StartNextRound(games, eventPublisher);
    }

    @Bean
    PickTetrominoUseCase pickTetrominoUseCase(EventPublisher<TetrominoPickedEvent> eventPublisher) {
        return new PickTetromino(eventPublisher);
    }

    @Bean
    FallTetrominoUseCase moveTetrominoOnInitPositionUseCase(EventPublisher<TetrominoMovedEvent> eventPublisher, Boards boards) {
        return new FallTetromino(eventPublisher, boards);
    }


}
