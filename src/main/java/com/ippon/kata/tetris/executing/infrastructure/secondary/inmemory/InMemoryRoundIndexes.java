package com.ippon.kata.tetris.executing.infrastructure.secondary.inmemory;

import com.ippon.kata.tetris.executing.application.domain.RoundIndex;
import com.ippon.kata.tetris.executing.application.domain.RoundIndexes;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoundIndexes implements RoundIndexes {
  private final Map<UUID, Integer> roundIndexes = new ConcurrentHashMap<>();

  @Override
  public RoundIndex get(GameId gameId) {
    return new RoundIndex(roundIndexes.get(gameId.value()), gameId);
  }

  @Override
  public RoundIndex add(RoundIndex roundIndex) {
    roundIndexes.put(roundIndex.gameId().value(), roundIndex.value());
    return roundIndex;
  }
}
