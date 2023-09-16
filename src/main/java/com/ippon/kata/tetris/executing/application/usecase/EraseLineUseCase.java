package com.ippon.kata.tetris.executing.application.usecase;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;
import com.ippon.kata.tetris.shared.domain.Level;

public interface EraseLineUseCase {

  LinesErasedEvent eraseCompletedLines(BoardId boardId, Level level);
}
