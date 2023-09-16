package com.ippon.kata.tetris.executing.infrastructure.secondary.inmemory;

import com.ippon.kata.tetris.executing.application.domain.RoundIndex;
import com.ippon.kata.tetris.executing.application.domain.RoundIndexes;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRoundIndexes implements RoundIndexes {
  private final AtomicInteger atomicInteger;

  public InMemoryRoundIndexes() {
    atomicInteger = new AtomicInteger(0);
  }

  @Override
  public RoundIndex get() {
    return new RoundIndex(atomicInteger.get());
  }

  @Override
  public RoundIndex add(RoundIndex roundIndex) {
    atomicInteger.set(roundIndex.value());
    return new RoundIndex(atomicInteger.get());
  }
}
