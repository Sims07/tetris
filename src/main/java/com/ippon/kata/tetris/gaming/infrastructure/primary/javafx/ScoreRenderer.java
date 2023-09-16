package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.scoring.infrastructure.secondary.spring.ScoreUpdatedEventDTO;
import javafx.scene.canvas.GraphicsContext;

public class ScoreRenderer extends AbstractRenderer<ScoreUpdatedEventDTO> {
  public static final String SCORE_S = "Score : %s";
  public static final int X = WIDTH * BLOCK_SIZE + TEXT_HEIGHT;
  public static final double Y = HEIGHT * BLOCK_SIZE / HALF + TEXT_HEIGHT;

  @Override
  public void render(GraphicsContext graphicsContext, ScoreUpdatedEventDTO event) {
    renderTextAt(graphicsContext, X, Y, SCORE_S.formatted(event.score()));
  }
}