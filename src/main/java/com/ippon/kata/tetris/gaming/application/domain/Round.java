package com.ippon.kata.tetris.gaming.application.domain;

public record Round(RoundStatus status, int index) {

  public Round next() {
    return new Round(RoundStatus.STARTED, index + 1);
  }
}
