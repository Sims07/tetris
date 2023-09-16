package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.List;
import java.util.function.UnaryOperator;

public interface Games {

  Game add(Game game);

  Game update(Game game);

  Game update(GameId gameId, UnaryOperator<Game> updatedFonction);

  Game get(GameId gameId);

  List<Game> list();
}
