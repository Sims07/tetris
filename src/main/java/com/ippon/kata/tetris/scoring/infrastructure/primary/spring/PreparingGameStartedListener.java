package com.ippon.kata.tetris.scoring.infrastructure.primary.spring;

import com.ippon.kata.tetris.gaming.infrastructure.secondary.spring.GameStartedEventDTO;
import com.ippon.kata.tetris.preparing.infrastructure.primary.spring.GameStartedListener;
import com.ippon.kata.tetris.scoring.application.usecase.InitializeScoreUseCase;
import com.ippon.kata.tetris.shared.domain.GameId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PreparingGameStartedListener implements ApplicationListener<GameStartedEventDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameStartedListener.class);
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
