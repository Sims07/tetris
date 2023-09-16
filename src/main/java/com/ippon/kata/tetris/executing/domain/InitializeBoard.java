package com.ippon.kata.tetris.executing.domain;

import com.ippon.kata.tetris.executing.usecase.InitializeBoardUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitializeBoard implements InitializeBoardUseCase {


    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeBoard.class);
    private final Boards boards;
    private final EventPublisher<BoardInitializedEvent> eventPublisher;

    public InitializeBoard(Boards boards, EventPublisher<BoardInitializedEvent> eventPublisher) {
        this.boards = boards;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public BoardInitializedEvent init(GameId gameId) {
        LOGGER.info("EXECUTING : Command Initialize board");
        boards.save(new Board(
            new BoardId(gameId)
        ));
        final BoardInitializedEvent boardInitializedEvent = new BoardInitializedEvent(gameId);
        eventPublisher.publish(boardInitializedEvent);
        return boardInitializedEvent;
    }


}
