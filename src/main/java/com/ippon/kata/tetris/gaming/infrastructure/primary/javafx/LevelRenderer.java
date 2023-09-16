package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.NextRoundStartedEventDTO;
import com.ippon.kata.tetris.shared.primary.javafx.AbstractRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LevelRenderer extends AbstractRenderer<NextRoundStartedEventDTO> {
  public static final String LEVEL = "Level : %s";
  public static final int X = WIDTH * BLOCK_SIZE + TEXT_HEIGHT;
  public static final double Y = HEIGHT * BLOCK_SIZE / HALF + LEVEL_Y_OFFSET;

  @Override
  public void render(GraphicsContext graphicsContext, NextRoundStartedEventDTO event) {
    renderTextAt(graphicsContext, X, Y, LEVEL.formatted(event.level()), Color.BLACK);
  }

}
