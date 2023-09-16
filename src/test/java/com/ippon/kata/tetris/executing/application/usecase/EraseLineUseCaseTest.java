package com.ippon.kata.tetris.executing.application.usecase;

import static com.ippon.kata.tetris.executing.application.domain.Board.NB_COLUMNS;
import static com.ippon.kata.tetris.executing.application.domain.Board.NB_LINES;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.executing.application.domain.Board;
import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.Boards;
import com.ippon.kata.tetris.executing.application.domain.EraseLine;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import com.ippon.kata.tetris.executing.application.domain.Position;
import com.ippon.kata.tetris.executing.application.domain.TetraminoStatus;
import com.ippon.kata.tetris.executing.application.domain.Tetromino;
import com.ippon.kata.tetris.executing.domain.TetrominoFixture;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EraseLineUseCaseTest {

  EraseLineUseCase eraseLineUseCase;
  @Mock Boards boards;
  @Mock EventPublisher<LinesErasedEvent> linesErasedEventEventPublisher;

  @BeforeEach
  void init() {
    eraseLineUseCase = new EraseLine(boards, linesErasedEventEventPublisher);
  }

  @Test
  void givenMoveOneCompleteLine_move_shouldPublishEventWithNbLineErased() {
    final GameId gameId = new GameId(UUID.randomUUID());
    final BoardId boardId = new BoardId(gameId);
    Map<Position, Optional<Tetromino>> slots = fillLine(Board.emptySlots(), NB_LINES - 1);
    slots = fillLine(slots, NB_LINES - 2);
    final Board board =
        new Board(boardId, slots, Optional.empty());
    given(linesErasedEventEventPublisher.publish(any())).willAnswer(i -> i.getArguments()[0]);
    given(boards.save(any())).willAnswer(i -> i.getArguments()[0]);
    given(boards.get(boardId)).willReturn(board);

    final LinesErasedEvent linesErasedEvent = eraseLineUseCase.eraseCompletedLines(boardId);

    then(linesErasedEvent).isNotNull();
    then(linesErasedEvent.erasedLines().size()).isEqualTo(2);
    then(linesErasedEvent.erasedLines().get(0).value()).isEqualTo(21);
    then(linesErasedEvent.erasedLines().get(1).value()).isEqualTo(20);
  }

  private Map<Position, Optional<Tetromino>> fillLine(
      Map<Position, Optional<Tetromino>> positionOptionalMap, int lineIndexToFill) {
    Map<Position, Optional<Tetromino>> emptyBoard = new HashMap<>(NB_COLUMNS * NB_LINES);
    IntStream.range(0, NB_LINES)
        .forEach(
            i ->
                IntStream.range(0, NB_COLUMNS)
                    .forEach(
                        j ->
                        {
                          final Position position = new Position(j, i);
                          emptyBoard.put(
                              position,
                              i == lineIndexToFill
                                  ? Optional.of(
                                      TetrominoFixture.tetromino(
                                          ShapeType.L, TetraminoStatus.FIXED))
                                  : positionOptionalMap.get(position));
                        }));
    return emptyBoard;
  }
}
