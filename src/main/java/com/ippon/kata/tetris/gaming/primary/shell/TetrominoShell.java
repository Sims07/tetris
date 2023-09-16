package com.ippon.kata.tetris.gaming.primary.shell;

import com.ippon.kata.tetris.executing.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.gaming.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.Direction;
import java.util.Scanner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TetrominoShell {
    private final TetrisGameStartUseCase tetrisGameStartUseCase;
    private final MoveTetrominoUseCase moveTetrominoUseCase;

    public TetrominoShell(TetrisGameStartUseCase tetrisGameStartUseCase, MoveTetrominoUseCase moveTetrominoUseCase) {
        this.tetrisGameStartUseCase = tetrisGameStartUseCase;
        this.moveTetrominoUseCase = moveTetrominoUseCase;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        final GameStartedEvent gameStartedEvent = tetrisGameStartUseCase.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            final String line = scanner.nextLine();
            switch (line) {
                case "l" -> moveTetrominoUseCase.move(gameStartedEvent.gameId(), Direction.LEFT);
                case "r" -> moveTetrominoUseCase.move(gameStartedEvent.gameId(), Direction.RIGHT);
                default -> moveTetrominoUseCase.move(gameStartedEvent.gameId(), Direction.DOWN);
            }
        }
    }
}
