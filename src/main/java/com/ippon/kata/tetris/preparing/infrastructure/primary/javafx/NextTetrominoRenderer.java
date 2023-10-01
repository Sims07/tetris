package com.ippon.kata.tetris.preparing.infrastructure.primary.javafx;

import com.ippon.kata.tetris.preparing.infrastructure.secondary.spring.TetrominoGeneratedEventDTO;
import com.ippon.kata.tetris.shared.domain.Shape;
import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.infrastructure.primary.javafx.AbstractRenderer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class NextTetrominoRenderer extends AbstractRenderer<TetrominoGeneratedEventDTO> {
  public static final String TETROMINO_SUIVANT = "Tetromino suivant";
  public static final int TETROMINO_BLOC_SIZE = 4;

  @Override
  public void render(GraphicsContext graphicsContext, TetrominoGeneratedEventDTO event) {
    final int xOrigin = xOrigin();
    final int yOrigin = 3 * BLOCK_SIZE;
    clearPreviousTetromino(graphicsContext, xOrigin, yOrigin);
    wrapInShape(event.getShape())
        .initPositions()
        .forEach(
            position ->
                renderTetromino(
                    graphicsContext,
                    ShapeType.valueOf(event.getShape()),
                    xOrigin + (position.x() - TETROMINO_BLOC_SIZE) * BLOCK_SIZE,
                    yOrigin + (position.y()) * BLOCK_SIZE));
  }

  private Shape wrapInShape(String shape) {
    return new Shape(ShapeType.valueOf(shape));
  }

  private void clearPreviousTetromino(GraphicsContext graphicsContext, int x, int y) {
    final double blockSize = BLOCK_SIZE;
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillText(TETROMINO_SUIVANT, x - blockSize, y - blockSize - PADDING);
    graphicsContext.strokeRect(
        x - blockSize,
        y - blockSize,
        (1 + TETROMINO_BLOC_SIZE) * blockSize,
        TETROMINO_BLOC_SIZE * blockSize);
    graphicsContext.setFill(Color.LIGHTGRAY);
    graphicsContext.fillRect(
        x - blockSize,
        y - blockSize,
        (1 + TETROMINO_BLOC_SIZE) * blockSize,
        TETROMINO_BLOC_SIZE * blockSize);
  }

  private static void renderTetromino(
      GraphicsContext graphicsContext, ShapeType tetrominoDTO, int x, int y) {
    setTetrominoFillColor(graphicsContext, tetrominoDTO);

    graphicsContext.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
  }

  private static void setTetrominoFillColor(GraphicsContext graphicsContext, ShapeType shape) {
    graphicsContext.setFill(
        switch (shape) {
          case I -> Color.YELLOW;
          case J -> Color.AQUA;
          case L -> Color.AZURE;
          case O -> Color.SALMON;
          case S -> Color.LIGHTGREEN;
          case T -> Color.PINK;
          case Z -> Color.BLUEVIOLET;
        });
  }
}
