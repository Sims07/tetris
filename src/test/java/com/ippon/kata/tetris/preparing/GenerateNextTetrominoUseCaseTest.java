package com.ippon.kata.tetris.preparing;

import static org.assertj.core.api.BDDAssertions.then;

import com.ippon.kata.tetris.preparing.domain.GenerateNextTetromino;
import com.ippon.kata.tetris.preparing.domain.GenerateNextTetrominoUseCase;
import com.ippon.kata.tetris.preparing.domain.TetrominoGeneratedEvent;
import com.ippon.kata.tetris.preparing.secondary.spring.TetrominoGeneratedPublisher;
import com.ippon.kata.tetris.shared.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenerateNextTetrominoUseCaseTest {

    @Mock
    TetrominoGeneratedPublisher eventPublisher;

    @Test
    void generateNextTetromino_shouldSaveAndPublishEvent() {
        GenerateNextTetrominoUseCase generateNextTetrominoUseCase = new GenerateNextTetromino(eventPublisher);

        final TetrominoGeneratedEvent tetrominoGeneratedEvent = generateNextTetrominoUseCase.generateNextTetromino(
            new GameId(UUID.randomUUID())
        );

        then(tetrominoGeneratedEvent).isNotNull();
        BDDMockito.then(eventPublisher).should().publish(tetrominoGeneratedEvent);
    }
}