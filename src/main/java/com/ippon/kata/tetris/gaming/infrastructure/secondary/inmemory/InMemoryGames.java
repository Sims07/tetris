package com.ippon.kata.tetris.gaming.infrastructure.secondary.inmemory;

import com.ippon.kata.tetris.gaming.application.domain.Game;
import com.ippon.kata.tetris.gaming.application.domain.Games;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import org.springframework.stereotype.Component;

@Component
public class InMemoryGames implements Games {

  private final Map<GameId, Game> gameStore = new ConcurrentHashMap<>();

  @Override
  public Game add(Game game) {
    gameStore.put(game.id(), game);
    return game;
  }

  @Override
  public Game update(Game game) {
    if (gameStore.get(game.id()) == null) {
      throw new IllegalArgumentException("Game should exist to be updated");
    }
    return add(game);
  }

  @Override
  public Game update(GameId gameId, UnaryOperator<Game> updatedFonction) {
    return gameStore.computeIfPresent(gameId, (gameIdToUpdate, gameToUpdate) -> updatedFonction.apply(gameToUpdate));
  }


  @Override
  public Game get(GameId gameId) {
    return gameStore.get(gameId);
  }

  @Override
  public List<Game> list() {
    return gameStore.values().stream().toList();
  }
}
