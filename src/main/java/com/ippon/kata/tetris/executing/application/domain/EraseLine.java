package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.executing.application.usecase.EraseLineUseCase;
import com.ippon.kata.tetris.shared.domain.Level;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import java.util.List;

public class EraseLine implements EraseLineUseCase {
  private final Boards boards;

  private final EventPublisher<LinesErasedEvent> linesErasedEventEventPublisher;

  public EraseLine(Boards boards, EventPublisher<LinesErasedEvent> linesErasedEventEventPublisher) {
    this.boards = boards;
    this.linesErasedEventEventPublisher = linesErasedEventEventPublisher;
  }

  @Override
  public LinesErasedEvent eraseCompletedLines(BoardId boardId, Level level) {
    Board boardWithoutCompleteLines = boards.get(boardId);
    List<LineIndex> lineToErase = boardWithoutCompleteLines.lineToErase();
    if (lineToErase.isEmpty()) {
      return new LinesErasedEvent(lineToErase, boardId.gameId(), level);
    } else {
      boardWithoutCompleteLines = boardWithoutCompleteLines.eraseLines(lineToErase);
      boards.save(boardWithoutCompleteLines);
      return linesErasedEventEventPublisher.publish(new LinesErasedEvent(lineToErase, boardId.gameId(), level));
    }
  }
}
