package com.ippon.kata.tetris.gaming.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.List;

public interface Games {

  Game add(Game game);
  Game update(Game game);

  Game get(GameId gameId);

  List<Game> list();
}
