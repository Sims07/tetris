package com.ippon.kata.tetris.executing.application.usecase;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.LinesErasedEvent;

public interface EraseLineUseCase {

    LinesErasedEvent eraseCompletedLines(BoardId boardId);
}
