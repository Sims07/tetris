package com.ippon.kata.tetris.scoring.application.usecase;

import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import com.ippon.kata.tetris.scoring.application.domain.Score;
import com.ippon.kata.tetris.scoring.application.domain.ScoreUpdatedEvent;
import com.ippon.kata.tetris.scoring.application.domain.Scores;
import com.ippon.kata.tetris.scoring.application.domain.UpdateScore;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.UUID;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateScoreUseCaseTest {
  UpdateScoreUseCase updateScoreUseCase;
  @Mock Scores scores;

  @BeforeEach
  void init() {
    updateScoreUseCase = new UpdateScore(scores);
  }

  enum ExpectedScore {
    SINGLE(1, 100),
    DOUBLE(2, 300),
    TRIPLE(3, 500),
    TETRIS(4, 800);

    private final int expectedScore;
    private final int nbLineErased;

    ExpectedScore(int nbLineErased, int expectedScore) {
      this.nbLineErased = nbLineErased;
      this.expectedScore = expectedScore;
    }
  }

  @ParameterizedTest
  @EnumSource(ExpectedScore.class)
  void givenXLineErased_score_shouldIncreaseOf40xNbLines(ExpectedScore expectedScore) {
    final GameId gameId = new GameId(UUID.randomUUID());
    given(scores.get(any())).willReturn(new Score(gameId, 0));
    given(scores.save(any())).willAnswer(a -> a.getArguments()[0]);

    final ScoreUpdatedEvent scoreUpdatedEvent = updateScoreUseCase.erasedLines(gameId, expectedScore.nbLineErased);

    BDDAssertions.then(scoreUpdatedEvent).isNotNull();
    BDDAssertions.then(scoreUpdatedEvent.score()).isNotNull();
    BDDAssertions.then(scoreUpdatedEvent.score().value()).isEqualTo(expectedScore.expectedScore);
  }
}
