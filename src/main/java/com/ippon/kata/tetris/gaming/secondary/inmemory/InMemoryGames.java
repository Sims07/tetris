package com.ippon.kata.tetris.gaming.secondary.inmemory;

import com.ippon.kata.tetris.gaming.Games;
import com.ippon.kata.tetris.gaming.domain.Game;
import com.ippon.kata.tetris.shared.GameId;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class InMemoryGames implements Games {

    private final Map<GameId, Game> gameStore = new HashMap<>();

    public InMemoryGames() {
    }

    @Override
    public synchronized Game save(Game game) {
        gameStore.put(game.id(), game);
        return game;
    }

    @Override
    public synchronized Game get(GameId gameId) {
        return gameStore.get(gameId);
    }
}
