package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.shared.primary.javafx.AbstractRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class StartButtonRenderer extends AbstractRenderer<Void> {

  public static final String START_BUTTON_LABEL = "Démarrer";

  @Override
  public void render(GraphicsContext graphicsContext, Void event) {
    final double y = yStartButton();

    graphicsContext.setFill(Color.LIGHTGRAY);
    graphicsContext.fillRoundRect(
        xStartButton(), y, widthStartButton(), heightStartButton(), 10, 10);
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(START_BUTTON_LABEL, xStartButton() + PADDING, y + 2 * PADDING);
  }

  private static double yStartButton() {
    return (HEIGHT - 3.0) * BLOCK_SIZE;
  }

  private static double heightStartButton() {
    return BLOCK_SIZE * 1.5;
  }

  public static boolean inYStartButtonRange(MouseEvent mouseEvent) {
    return mouseEvent.getY() > yStartButton()
        && mouseEvent.getY() < yStartButton() + heightStartButton();
  }

  public static boolean inXStartButtonRange(MouseEvent mouseEvent) {
    return mouseEvent.getX() > xStartButton()
        && mouseEvent.getX() < xStartButton() + widthStartButton();
  }

  private static double widthStartButton() {
    return BLOCK_SIZE * 3.5;
  }

  private static int xStartButton() {
    return xOrigin();
  }
}
