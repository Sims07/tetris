package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.executing.application.usecase.FallTetrominoUseCase;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;

public class FallTetromino implements FallTetrominoUseCase {

  private final EventPublisher<TetrominoMovedEvent> eventPublisher;
  private final Boards boards;

  public FallTetromino(EventPublisher<TetrominoMovedEvent> eventPublisher, Boards boards) {
    this.eventPublisher = eventPublisher;
    this.boards = boards;
  }

    @Override
    public TetrominoMovedEvent fall(
        BoardId boardId,
        Tetromino tetromino) {
        try {
            final Board board = boards.get(boardId);
            final Board boardToSave = board.move(tetromino, Direction.DOWN);
            final Board savedBoard = boards.save(boardToSave);
            return eventPublisher.publish(new TetrominoMovedEvent(
                boardId.gameId(),
                savedBoard.fallingTetromino().orElse(tetromino),
                Direction.DOWN,
                false
            ));
        } catch (TetrominoFixedException tetrominoFixedException) {
            boards.save(tetrominoFixedException.board());
            return eventPublisher.publish(new TetrominoMovedEvent(
                boardId.gameId(),
                tetromino.fixe(),
                Direction.DOWN,
                tetrominoFixedException.outOfScope()
            ));
        }
    }
}
