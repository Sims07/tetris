package com.ippon.kata.tetris.executing.infrastructure.primary.spring;

import com.ippon.kata.tetris.executing.application.domain.TetrominoMovedEvent;
import com.ippon.kata.tetris.executing.application.usecase.MoveTetrominoUseCase;
import com.ippon.kata.tetris.shared.domain.Direction;
import com.ippon.kata.tetris.shared.domain.GameId;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TetrominoAPI {
    private  final MoveTetrominoUseCase moveTetrominoUseCase;

    public TetrominoAPI(MoveTetrominoUseCase moveTetrominoUseCase) {
        this.moveTetrominoUseCase = moveTetrominoUseCase;
    }

    public Optional<TetrominoMovedEvent> move(GameId gameId, Direction direction){
        return moveTetrominoUseCase.move(gameId, direction);
    }
}
