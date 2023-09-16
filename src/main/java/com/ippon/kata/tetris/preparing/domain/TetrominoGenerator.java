package com.ippon.kata.tetris.preparing.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.Optional;
import java.util.Random;

public record TetrominoGenerator() {

    public Optional<Tetromino> current() {
        return Optional.empty();
    }

    public TetrominoGeneratedEvent generate(GameId gameId) {
        return new TetrominoGeneratedEvent(
            new Tetromino(
                ShapeType.J
            ),
            gameId
        );
    }

    private int randomIndex() {
        Random random = new Random();
        return random.ints(0, ShapeType.values().length)
            .findFirst()
            .orElse(0);
    }


}
