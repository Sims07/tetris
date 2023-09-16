package com.ippon.kata.tetris.executing.application.usecase;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.executing.application.domain.Board;
import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.Boards;
import com.ippon.kata.tetris.executing.application.domain.MoveTetromino;
import com.ippon.kata.tetris.executing.application.domain.Position;
import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.application.domain.TetrominoFixture;
import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.executing.infrastructure.secondary.spring.TetrominoMovedPublisher;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoveTetrominoUseCaseTest {

  MoveTetrominoUseCase moveTetrominoUseCase;
  @Mock Boards boards;
  @Mock TetrominoMovedPublisher tetrominoMovedPublisher;

  @BeforeEach
  void init() {
    moveTetrominoUseCase = new MoveTetromino(boards, tetrominoMovedPublisher);
  }

  @Test
  void givenNoTetrominoReadyToFall_move_shouldDoNothing() {
    final GameId gameId = new GameId(UUID.randomUUID());
    final BoardId boardId = new BoardId(gameId);
    given(boards.get(boardId)).willReturn(new Board(boardId));

    final Optional<TetrominoMovedEvent> tetrominoMovedEvent =
        moveTetrominoUseCase.move(gameId, Direction.DOWN);

    then(tetrominoMovedEvent).isEmpty();
  }

  @ParameterizedTest
  @EnumSource(Direction.class)
  void givenFallingITetrominoWithNextSlotAvailable_move_shouldMoveTetromino(Direction direction) {
    final GameId gameId = new GameId(UUID.randomUUID());
    final BoardId boardId = new BoardId(gameId);
    final Board board = new Board(boardId);
    final Board boardWithFallingTetromino =
        board.move(TetrominoFixture.tetromino(ShapeType.I, TetraminoStatus.IDLE), Direction.DOWN);
    given(boards.get(boardId)).willReturn(boardWithFallingTetromino);
    given(tetrominoMovedPublisher.publish(any())).willAnswer(i -> i.getArguments()[0]);

    final Optional<TetrominoMovedEvent> tetrominoMovedEvent =
        moveTetrominoUseCase.move(gameId, direction);

    then(tetrominoMovedEvent).isNotEmpty();
    BDDMockito.then(boards).should().save(any());
    thenTetrominoShouldBeAt(tetrominoMovedEvent.get().tetromino(), direction);
  }

    private void thenTetrominoShouldBeAt(Tetromino tetromino, Direction direction) {
        switch (direction) {
            case DOWN -> then(tetromino.positions())
                .isEqualTo(
                    List.of(new Position(3, 1)
                        , new Position(4, 1)
                        , new Position(5, 1)
                        , new Position(6, 1))
                );
            case LEFT -> then(tetromino.positions())
                .isEqualTo(
                    List.of(new Position(2, 0)
                        , new Position(3, 0)
                        , new Position(4, 0)
                        , new Position(5, 0))
                );
            case RIGHT -> then(tetromino.positions())
                .isEqualTo(
                    List.of(new Position(4, 0)
                        , new Position(5, 0)
                        , new Position(6, 0)
                        , new Position(7, 0))
                );
        }
    }
}
