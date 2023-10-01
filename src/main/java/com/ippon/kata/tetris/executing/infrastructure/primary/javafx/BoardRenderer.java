package com.ippon.kata.tetris.executing.infrastructure.primary.javafx;

import com.ippon.kata.tetris.shared.infrastructure.primary.javafx.AbstractRenderer;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model.BoardDTO;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model.TetrominoDTO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardRenderer extends AbstractRenderer<BoardDTO> {

  @Override
  public void render(GraphicsContext graphicsContext, BoardDTO board) {
    board
        .slots()
        .forEach(
            (position, optTetromino) -> {
              if (optTetromino.isEmpty()) {
                renderEmptySlot(graphicsContext, Color.GRAY, position.y(), position.x());
              } else {
                renderTetromino(
                    graphicsContext,
                    optTetromino.get(),
                    position.y() * BLOCK_SIZE,
                    position.x() * BLOCK_SIZE,
                    false);
              }
            });
    board
        .tetrominoProjection()
        .ifPresent(
            tetrominoDTO ->
                tetrominoDTO
                    .positions()
                    .forEach(
                        position ->
                            renderTetromino(
                                graphicsContext,
                                tetrominoDTO,
                                position.y() * BLOCK_SIZE,
                                position.x() * BLOCK_SIZE,
                                true)));
  }

  private static void renderEmptySlot(
      GraphicsContext graphicsContext, Color gray, int key, int key1) {
    graphicsContext.setFill(gray);
    graphicsContext.fillRect(
        (double) key * BLOCK_SIZE, (double) key1 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(
        (double) key * BLOCK_SIZE, (double) key1 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
  }

  private static void renderTetromino(
      GraphicsContext graphicsContext, TetrominoDTO tetromino, int x, int y, boolean projected) {
    if (projected) {
      setProjectedTetrominoFillColor(graphicsContext, tetromino);
    } else {
      setTetrominoFillColor(graphicsContext, tetromino);
    }

    graphicsContext.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
    graphicsContext.strokeRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
  }

  private static void setProjectedTetrominoFillColor(
      GraphicsContext graphicsContext, TetrominoDTO tetromino) {
    graphicsContext.setFill(
        switch (tetromino.shape()) {
          case I -> Color.LIGHTYELLOW;
          case J, L -> Color.LIGHTBLUE;
          case O -> Color.LIGHTCORAL;
          case S -> Color.LIGHTGREEN;
          case T -> Color.LIGHTPINK;
          case Z -> Color.LIGHTSKYBLUE;
        });
  }

  private static void setTetrominoFillColor(
      GraphicsContext graphicsContext, TetrominoDTO tetromino) {
    graphicsContext.setFill(
        switch (tetromino.shape()) {
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
