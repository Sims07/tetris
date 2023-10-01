package com.ippon.kata.tetris.gaming.infrastructure.primary.javafx;

import com.ippon.kata.tetris.shared.infrastructure.primary.javafx.AbstractRenderer;
import com.ippon.kata.tetris.shared.infrastructure.primary.javafx.TetrominoGame;
import com.ippon.kata.tetris.shared.infrastructure.secondary.spring.model.TetrominoMovedEventDTO;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class LostGameRenderer extends AbstractRenderer<TetrominoMovedEventDTO> {
  private static final URL LOST_SOUND = TetrominoGame.class.getResource("/sounds/lost.wav");
  public static final String BLANK = "";
  public static final String LOST_LABEL = "Perdu";

  @Override
  public void render(GraphicsContext graphicsContext, TetrominoMovedEventDTO event) {
    lostSound();
    renderLostGame(graphicsContext);
  }

  private void renderLostGame(GraphicsContext graphicsContext) {
    final Paint initialFill = graphicsContext.getFill();
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillRect(0, 0, boardWidth(), boardHeight());
    graphicsContext.strokeRect(0, 0, boardWidth(), boardHeight());
    graphicsContext.setFill(Color.WHITE);
    graphicsContext.fillText(
        LostGameRenderer.LOST_LABEL, boardWidth() / 2.0 - BLOCK_SIZE, boardHeight() / 2.0);
    graphicsContext.setFill(initialFill);
  }

  private void lostSound() {
    final AudioClip audioClip;
    try {
      audioClip = new AudioClip(LOST_SOUND.toURI().toString());
      audioClip.setVolume(5);
      audioClip.play();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
