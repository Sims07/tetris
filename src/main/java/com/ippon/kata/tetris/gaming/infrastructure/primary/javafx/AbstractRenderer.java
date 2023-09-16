package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class AbstractRenderer<E> implements Renderer<E> {
  static int xOrigin() {
    return boardWidth() + boardWidth() / 2 - BLOCK_SIZE;
  }

  static int boardWidth() {
    return WIDTH * BLOCK_SIZE;
  }

  static void setDefaultFillColor(GraphicsContext graphicsContext) {
    graphicsContext.setFill(Color.WHITE);
  }

  static void renderTextAt(GraphicsContext graphicsContext, int x, double y, String text) {
    setDefaultFillColor(graphicsContext);
    graphicsContext.fillRect(x, y - TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(text, x, y);
  }
}
