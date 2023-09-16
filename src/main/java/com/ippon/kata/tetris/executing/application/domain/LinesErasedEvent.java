package com.ippon.kata.tetris.executing.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.Level;
import java.util.List;

public record LinesErasedEvent(List<LineIndex> erasedLines, GameId gameId, Level level) {

}
