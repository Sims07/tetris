package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.shared.primary.javafx.AbstractRenderer;
import com.ippon.kata.tetris.shared.secondary.spring.model.TetrominoMovedEventDTO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LostGameRenderer extends AbstractRenderer<TetrominoMovedEventDTO> {

  public static final String BLANK = "";
  public static final String LOST_LABEL = "Perdu";

  @Override
  public void render(GraphicsContext graphicsContext, TetrominoMovedEventDTO event) {
    renderLostGame(graphicsContext, LOST_LABEL);
  }

  @Override
  public void erase(GraphicsContext graphicsContext) {
    renderLostGame(graphicsContext, BLANK);
  }

  private static void renderLostGame(GraphicsContext graphicsContext, String label) {
    renderTextAt(
        graphicsContext, boardWidth() + 0.5 * boardWidth(), boardHeight() / 2.0, label, Color.RED);
  }
}
