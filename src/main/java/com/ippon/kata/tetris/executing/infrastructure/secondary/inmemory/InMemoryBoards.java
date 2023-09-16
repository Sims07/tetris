package com.ippon.kata.tetris.executing.infrastructure.secondary.inmemory;

import com.ippon.kata.tetris.executing.application.domain.Board;
import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.Boards;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryBoards implements Boards {

  private final Map<BoardId, Board> boards = new ConcurrentHashMap<>();

  @Override
  public Board save(Board board) {
    boards.put(board.boardId(), board);
    return board;
  }

  @Override
  public Board get(BoardId boardId) {
    return boards.get(boardId);
  }
}
