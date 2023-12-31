package com.ippon.kata.tetris.gaming.infrastructure.primary.shell;

import com.ippon.kata.tetris.executing.application.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.gaming.application.domain.GameStartedEvent;
import com.ippon.kata.tetris.gaming.application.usecase.TetrisGameStartUseCase;
import com.ippon.kata.tetris.shared.domain.Direction;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class TetrominoShell {
    private final TetrisGameStartUseCase tetrisGameStartUseCase;
    private final MoveTetrominoUseCase moveTetrominoUseCase;

    public TetrominoShell(TetrisGameStartUseCase tetrisGameStartUseCase, MoveTetrominoUseCase moveTetrominoUseCase) {
        this.tetrisGameStartUseCase = tetrisGameStartUseCase;
        this.moveTetrominoUseCase = moveTetrominoUseCase;
    }

    //@EventListener(ApplicationReadyEvent.class)
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
