package com.ippon.kata.tetris.executing.application.domain;

public interface Boards {

    Board save(Board board);

    Board get(BoardId boardId);

}
