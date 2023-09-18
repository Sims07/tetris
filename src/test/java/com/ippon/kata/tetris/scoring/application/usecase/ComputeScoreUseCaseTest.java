package com.ippon.kata.tetris.scoring.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import com.ippon.kata.tetris.scoring.application.domain.ComputeScore;
import com.ippon.kata.tetris.scoring.application.domain.Score;
import com.ippon.kata.tetris.scoring.application.domain.ScoreComputedEvent;
import com.ippon.kata.tetris.scoring.application.domain.Scores;
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
class ComputeScoreUseCaseTest {
  ComputeScoreUseCase computeScoreUseCase;
  @Mock Scores scores;

  @BeforeEach
  void init() {
    computeScoreUseCase = new ComputeScore(scores);
  }

  enum ExpectedScore {
    SINGLE(1, 100, 1),
    DOUBLE(2, 300, 1),
    TRIPLE(3, 500, 1),
    TETRIS(4, 800, 1),
    SINGLE_LEVEL5(1, 500, 5),
    DOUBLE_LEVEL5(2, 1500, 5),
    TRIPLE_LEVEL5(3, 2500, 5),
    TETRIS_LEVEL5(4, 4000, 5);

    private final int expectedScore;
    private final int nbLineErased;
    private final int level;

    ExpectedScore(int nbLineErased, int expectedScore, int level) {
      this.nbLineErased = nbLineErased;
      this.expectedScore = expectedScore;
      this.level = level;
    }
  }

  @ParameterizedTest
  @EnumSource(ExpectedScore.class)
  void givenXLineErased_score_shouldIncreaseOf40xNbLines(ExpectedScore expectedScore) {
    final GameId gameId = new GameId(UUID.randomUUID());
    given(scores.get(any())).willReturn(new Score(gameId, 0));
    given(scores.save(any())).willAnswer(a -> a.getArguments()[0]);

    final ScoreComputedEvent scoreComputedEvent =
        computeScoreUseCase.compute(gameId, expectedScore.nbLineErased, expectedScore.level);

    BDDAssertions.then(scoreComputedEvent).isNotNull();
    BDDAssertions.then(scoreComputedEvent.score()).isNotNull();
    BDDAssertions.then(scoreComputedEvent.score().value()).isEqualTo(expectedScore.expectedScore);
  }
}
