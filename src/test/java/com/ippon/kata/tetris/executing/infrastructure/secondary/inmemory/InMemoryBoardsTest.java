package com.ippon.kata.tetris.executing.infrastructure.secondary.inmemory;

import static org.assertj.core.api.BDDAssertions.then;

import com.ippon.kata.tetris.executing.application.domain.Board;
import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class InMemoryBoardsTest {

    public static final int SPEED_MS = 100;

    @Test
    void save() {
        final InMemoryBoards inMemoryBoards = new InMemoryBoards();
        final BoardId boardId = new BoardId(new GameId(UUID.randomUUID()));
        inMemoryBoards.save(new Board(
                boardId
            )
        );

        final Board board = inMemoryBoards.get(boardId);

        then(board).isNotNull();
    }
}