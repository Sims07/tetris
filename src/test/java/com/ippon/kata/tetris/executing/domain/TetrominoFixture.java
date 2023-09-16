package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.shared.ShapeType;
import java.util.UUID;

public class TetrominoFixture {

    public static Tetromino tetromino(ShapeType shapeType) {
        final Shape shape = new Shape(shapeType);
        return new Tetromino(
            new TetrominoId(
                UUID.randomUUID())
            , shape
            , TetraminoStatus.IDLE
            , shape.initPositions()
        );
    }
}
