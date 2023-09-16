package com.ippon.kata.tetris.gaming.primary.spring;

import static com.ippon.kata.tetris.gaming.application.domain.RoundStatus.IDLE;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.gaming.application.domain.Round;
import com.ippon.kata.tetris.gaming.application.domain.Tetromino;
import com.ippon.kata.tetris.gaming.application.usecase.StartNextRoundUseCase;
import com.ippon.kata.tetris.gaming.infrastructure.primary.spring.GameListener;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardInitializedEventDTO;
import com.ippon.kata.tetris.shared.secondary.spring.model.TetrominoMovedEventDTO;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameListenerTest {

    @InjectMocks
    GameListener gameListener;
    @Mock
    Games games;
    @Mock
    StartNextRoundUseCase nextRoundUseCase;

    @Test
    void givenAllInitialized_onApplicationEvent_shouldSetGameAsPlayingAndStartANewRound() {
        final UUID gameIdValue = UUID.randomUUID();
        final GameId gameId = new GameId(gameIdValue);
        given(games.get(gameId)).willReturn(new Game(gameId, false, true, new Round(IDLE, 0), true, new Tetromino(ShapeType.S)));
        final Game game = new Game(gameId, true, true, new Round(IDLE, 0), true, new Tetromino(ShapeType.S));
        given(games.save(game)).willReturn(game);

        gameListener.onApplicationEvent(new BoardInitializedEventDTO(
            this,
            gameIdValue
        ));

        then(nextRoundUseCase).should().start(gameId, ShapeType.S);
    }

    @Test
    void givenOutOfScope_onTetrominoMovedEvent_shouldEmitLostGameEvent() {
        final UUID gameIdValue = UUID.randomUUID();
        final GameId gameId = new GameId(gameIdValue);

        gameListener.onApplicationEvent(new TetrominoMovedEventDTO(
            this,
            UUID.randomUUID(),
            ShapeType.L,
            Direction.DOWN,
            true,
            true,
            Collections.emptyList()));
        
        then(nextRoundUseCase).should(never()).start(gameId, ShapeType.S);
    }
}