package com.ippon.kata.tetris.scoring.primary.spring;

import com.ippon.kata.tetris.gaming.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.scoring.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.GameId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PreparingGameStartedListener implements ApplicationListener<GameStartedEventDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.ippon.kata.tetris.preparing.primary.spring.GameStartedListener.class);
    private final InitializeScoreUseCase initializeScoreUseCase;

    public PreparingGameStartedListener(InitializeScoreUseCase initializeScoreUseCase) {
        this.initializeScoreUseCase = initializeScoreUseCase;
    }

    @Override
    public void onApplicationEvent(GameStartedEventDTO event) {
        LOGGER.info("SCORING : Receive game started to {}", event.getGameId());
        initializeScoreUseCase.init(new GameId(event.getGameId()));
    }

}
