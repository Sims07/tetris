package com.ippon.kata.tetris.executing.infrastructure.primary.javafx;

import com.ippon.kata.tetris.shared.domain.ShapeType;
import com.ippon.kata.tetris.shared.primary.javafx.AbstractRenderer;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardDTO;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardRenderer extends AbstractRenderer<BoardDTO> {

    @Override
    public void render(GraphicsContext graphicsContext, BoardDTO board) {
        board
            .slots()
            .forEach(
                (key, value) -> {
                    if (value.isEmpty()) {
                        renderEmptySlot(graphicsContext, Color.GRAY, key.y(), key.x());
                    } else {
                        renderTetromino(
                            graphicsContext,
                            value.get().shape(),
                            key.y() * BLOCK_SIZE,
                            key.x() * BLOCK_SIZE);
                    }
                });
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
