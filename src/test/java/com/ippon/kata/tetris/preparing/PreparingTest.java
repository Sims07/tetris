package com.ippon.kata.tetris.preparing;

import static org.assertj.core.api.BDDAssertions.then;

import com.ippon.kata.tetris.preparing.application.domain.Tetromino;
import com.ippon.kata.tetris.preparing.application.domain.TetrominoGeneratedEvent;
import com.ippon.kata.tetris.preparing.application.domain.TetrominoGenerator;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PreparingTest {

    private static GameId getGameId() {
        return new GameId(UUID.randomUUID());
    }

    @Test
    void givenNoTetrominoDisplayed_current_shouldBeEmpty() {
        TetrominoGenerator tetrominoGenerator = new TetrominoGenerator();

        final Optional<Tetromino> current = tetrominoGenerator.current();

        then(current).isEmpty();
    }

    @Test
    void next_shouldReturnTetrominoGenerated() {
        TetrominoGenerator tetrominoGenerator = new TetrominoGenerator();

        final TetrominoGeneratedEvent tetrominoGeneratedEvent = tetrominoGenerator.generate(getGameId());

        then(tetrominoGeneratedEvent).isNotNull();
        then(tetrominoGeneratedEvent.tetromino()).isNotNull();
        then(tetrominoGeneratedEvent.tetromino().shape()).isNotNull();
    }
}
