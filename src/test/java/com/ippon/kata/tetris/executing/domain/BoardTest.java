package com.ippon.kata.tetris.executing.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BoardTest {

    private static HashMap<Position, Optional<Tetromino>> slotsWithTetromino(Board board1, Tetromino movedTetromino) {
        final HashMap<Position, Optional<Tetromino>> updatedSlots = new HashMap<>(board1.slots());
        movedTetromino.positions().forEach(position -> updatedSlots.put(position, Optional.of(movedTetromino)));
        return updatedSlots;
    }

    @Test
    void givenEmptyBoard_fallTetromino_shouldBeOnInitPosition() {
        final Board board = new Board(new BoardId(new GameId(UUID.randomUUID())));
        final Shape shape = new Shape(ShapeType.S);

        final Board board1 = board.fallTetromino(new Tetromino(new TetrominoId(UUID.randomUUID()), shape, TetraminoStatus.IDLE, shape.initPositions()));

        then(board1.fallingTetromino()).isNotEmpty();
        then(board1.fallingTetromino().map(Tetromino::positions).orElseThrow()).isEqualTo(shape.initPositions());
        board1.fallingTetromino().map(Tetromino::positions).orElseThrow().forEach(
            position -> {
                then(board1.slots().get(position).orElseThrow()).isNotNull();
            }
        );
    }

    @Test
    void givenEmptyBoardTetrominoPositionNextInitial_fallTetromino_shouldSetTetrominoStatusToMoving() {
        final Shape shape = new Shape(ShapeType.S);
        final Tetromino tetromino = new Tetromino(new TetrominoId(UUID.randomUUID()), shape,
            TetraminoStatus.MOVING,
            shape.initPositions());
        final Board board1 = new Board(
            new BoardId(new GameId(UUID.randomUUID())));
        final Board board = new Board(
            board1.boardId(),
            slotsWithTetromino(board1, tetromino),
            Optional.of(tetromino));

        final Board boardUpdated = board.fallTetromino(tetromino);

        then(boardUpdated.fallingTetromino().map(Tetromino::status).orElseThrow()).isEqualTo(TetraminoStatus.MOVING);
        then(boardUpdated).isNotNull();
        then(boardUpdated.slots().values().stream().filter(Optional::isPresent).count()).isEqualTo(4);
    }

    @Test
    void givenEmptyBoardTetrominoPositionUntilOutl_fallTetromino_shouldSetTetrominoStatusToFixed() {
        final Shape shape = new Shape(ShapeType.S);
        final Tetromino tetromino = new Tetromino(new TetrominoId(UUID.randomUUID()), shape,
            TetraminoStatus.MOVING,
            shape.initPositions().stream().map(p -> new Position(p.x() + Board.NB_LINES, p.y())).toList());
        final Board board1 = new Board(
            new BoardId(new GameId(UUID.randomUUID())));
        final Board board = new Board(
            board1.boardId(),
            slotsWithTetromino(board1, tetromino),
            Optional.of(tetromino));
        thenThrownBy(() ->
            board.fallTetromino(tetromino)).isInstanceOf(TetrominoFixedException.class);

    }
}