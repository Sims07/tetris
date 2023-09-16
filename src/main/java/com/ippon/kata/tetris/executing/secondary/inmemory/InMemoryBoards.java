package com.ippon.kata.tetris.executing.secondary.inmemory;

import com.ippon.kata.tetris.executing.domain.Board;
import com.ippon.kata.tetris.executing.domain.BoardId;
import com.ippon.kata.tetris.executing.domain.Boards;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryBoards implements Boards {

    private final Map<BoardId, Board> boards = new HashMap<>();

    @Override
    public synchronized Board save(Board board) {
        boards.put(board.boardId(), board);
        return board;
    }

    @Override
    public synchronized Board get(BoardId boardId) {
        return boards.get(boardId);
    }
}
