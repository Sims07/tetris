package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.executing.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.shared.Direction;
import com.ippon.kata.tetris.shared.GameId;
import java.util.Optional;

public class MoveTetromino implements MoveTetrominoUseCase {

    private final Boards boards;

    public MoveTetromino(Boards boards) {

        this.boards = boards;
    }

    @Override
    public Optional<TetrominoMovedEvent> move(GameId gameId, Direction direction) {
        final Board board = boards.get(new BoardId(gameId));
        return board.fallingTetromino()
            .map(tetromino -> {
                    Board updatedBoard = board.move(tetromino, direction);
                    boards.save(updatedBoard);
                    return new TetrominoMovedEvent(
                        gameId,
                        updatedBoard.fallingTetromino().orElseThrow(),
                        direction,
                        false);

                }
            );
    }
}
