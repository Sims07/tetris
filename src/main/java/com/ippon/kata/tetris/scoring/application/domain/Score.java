package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.shared.domain.GameId;

public record Score(GameId gameId, int value) {

  public Score erasedLines(int nbLinesErased, int level) {
    return new Score(gameId, value + bonus(nbLinesErased) * level);
  }

  private int bonus(int nbLinesErased) {
    return switch (nbLinesErased) {
      case 1 -> 100;
      case 2 -> 300;
      case 3 -> 500;
      case 4 -> 800;
      default -> 0;
    };
  }
}
