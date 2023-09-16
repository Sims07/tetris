package com.ippon.kata.tetris.executing.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.shared.Direction;
import com.ippon.kata.tetris.shared.EventPublisher;
import com.ippon.kata.tetris.shared.GameId;
import com.ippon.kata.tetris.shared.ShapeType;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FallTetrominoTest {

    @Mock
    Boards boards;
    @Mock
    EventPublisher<TetrominoMovedEvent> eventPublisher;

    @Test
    void givenNewFallingTetrominoOnEmptyBoard_fall_shouldSendMovedEvent() {
        final FallTetromino fallTetromino = new FallTetromino(eventPublisher, boards);
        final BoardId boardId = new BoardId(new GameId(UUID.randomUUID()));
        final Board boardGot = new Board(
            boardId
        );
        given(boards.get(boardId))
            .willReturn(boardGot);
        given(boards.save(any()))
            .willAnswer(args -> args.getArguments()[0]);
        final Shape shape = new Shape(ShapeType.S);
        final TetrominoMovedEvent tetrominoMovedEvent = new TetrominoMovedEvent(
            boardId.gameId(),
            new Tetromino(
                new TetrominoId(UUID.randomUUID()), shape,
                TetraminoStatus.MOVING,
                shape.initPositions()
            ),
            Direction.DOWN,
            false
        );
        given(eventPublisher.publish(any())).willReturn(tetrominoMovedEvent);

        final TetrominoMovedEvent movedEvent = fallTetromino.fall(
            boardId,
            new Tetromino(
                new TetrominoId(UUID.randomUUID()), shape,
                TetraminoStatus.MOVING,
                shape.initPositions()
            ));

        then(movedEvent).isNotNull();
        then(movedEvent.tetromino()).isNotNull();
        then(movedEvent.tetromino().positions()).isEqualTo(shape.initPositions());
    }

    @Test
    void givenTetrominoFixedException_fall_shouldEmitMovedEventFixed() {
        final Shape shape = new Shape(ShapeType.S);
        final FallTetromino fallTetromino = new FallTetromino(eventPublisher, boards);
        final BoardId boardId = new BoardId(new GameId(UUID.randomUUID()));
        final Board boardGot = new Board(
            boardId,
            Board.emptySlots(),
            Optional.of(new Tetromino(
                new TetrominoId(UUID.randomUUID()), shape,
                TetraminoStatus.MOVING,
                shape.initPositions()
                    .stream()
                    .map(position ->
                        new Position(position.x(), position.y() + Board.NB_LINES - 1))
                    .toList()
            ))
        );
        given(boards.get(boardId))
            .willReturn(boardGot);
        given(eventPublisher.publish(any())).willAnswer(args -> args.getArguments()[0]);

        final TetrominoMovedEvent movedEvent = fallTetromino.fall(
            boardId,
            new Tetromino(
                new TetrominoId(UUID.randomUUID()), shape,
                TetraminoStatus.MOVING,
                shape.initPositions()
            ));

        then(movedEvent).isNotNull();
        then(movedEvent.tetromino()).isNotNull();
        then(movedEvent.tetromino().status()).isEqualTo(TetraminoStatus.FIXED);
    }
}