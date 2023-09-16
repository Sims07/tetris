package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.executing.application.domain.RotationIndex;
import com.ippon.kata.tetris.executing.application.domain.Shape;
import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoId;
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
