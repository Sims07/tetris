package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.UUID;

public class TetrominoFixture {

    public static Tetromino tetromino(ShapeType shapeType, TetraminoStatus tetraminoStatus) {
        final Shape shape = new Shape(shapeType);
        return new Tetromino(
            new TetrominoId(
                UUID.randomUUID())
            , shape
            , tetraminoStatus
            , shape.initPositions(),
            new RotationIndex(0));
    }
}
