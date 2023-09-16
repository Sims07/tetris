package com.ippon.kata.tetris.executing.domain;

public interface Boards {

    Board save(Board board);

    Board get(BoardId boardId);

}
