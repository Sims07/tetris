package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.executing.secondary.spring.TetrominoMovedPublisher;
import com.ippon.kata.tetris.executing.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.Optional;

public class MoveTetromino implements MoveTetrominoUseCase {

    private final Boards boards;
    private final TetrominoMovedPublisher eventPublisher;

    public MoveTetromino(Boards boards, TetrominoMovedPublisher eventPublisher) {

        this.boards = boards;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<TetrominoMovedEvent> move(GameId gameId, Direction direction) {
        final Board board = boards.get(new BoardId(gameId));
        return board.fallingTetromino()
            .map(tetromino -> {
                    Board updatedBoard = board.move(tetromino, direction);
                    boards.save(updatedBoard);
                    return eventPublisher.publish(new TetrominoMovedEvent(
                        gameId,
                        updatedBoard.fallingTetromino().orElseThrow(),
                        direction,
                        false));

                }
            );
    }
}
