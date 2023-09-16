package com.ippon.kata.tetris.executing.application.domain;

import java.util.List;

public record LinesErasedEvent(List<LineIndex> erasedLines) {

}
