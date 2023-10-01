package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.shared.infrastructure.primary.javafx.AbstractRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class StartButtonRenderer extends AbstractRenderer<Void> {

  public static final String START_BUTTON_LABEL = "Nouvelle Partie";
  public static final double WIDTH = BLOCK_SIZE * 5;

  @Override
  public void render(GraphicsContext graphicsContext, Void event) {
    final double y = yStartButton();

    graphicsContext.setFill(Color.BLUE);
    graphicsContext.fillRoundRect(
        xStartButton(), y, widthStartButton(), heightStartButton(), 10, 10);
    graphicsContext.setFill(Color.WHITE);
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
    return WIDTH;
  }

  private static int xStartButton() {
    return xOrigin() - BLOCK_SIZE;
  }
}
