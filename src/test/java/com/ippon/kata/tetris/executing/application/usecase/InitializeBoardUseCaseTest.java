package com.ippon.kata.tetris.executing.application.usecase;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ippon.kata.tetris.executing.application.domain.BoardInitializedEvent;
import com.ippon.kata.tetris.executing.application.domain.Boards;
import com.ippon.kata.tetris.executing.application.domain.InitializeBoard;
import com.ippon.kata.tetris.shared.domain.EventPublisher;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InitializeBoardUseCaseTest {

  @Mock Boards boards;
  @Mock EventPublisher<BoardInitializedEvent> eventPublisher;

  @Test
  void givenGameId_init_shouldInitializedEmptyBoardAndEmitEvent() {
    InitializeBoardUseCase initializeBoardUseCase = new InitializeBoard(boards, eventPublisher);
    given(eventPublisher.publish(any())).willAnswer(args -> args.getArguments()[0]);
    final GameId gameId = new GameId(UUID.randomUUID());

    final BoardInitializedEvent boardInitializedEvent = initializeBoardUseCase.init(gameId);

    then(boardInitializedEvent).isNotNull();
    then(boardInitializedEvent.gameId()).isEqualTo(gameId);
    BDDMockito.then(boards).should().save(any());
    BDDMockito.then(eventPublisher).should().publish(boardInitializedEvent);
  }
}
