package com.ippon.kata.tetris.scoring.application.domain;

import com.ippon.kata.tetris.shared.asserts.Asserts;

public record ScoreUpdatedEvent(Score score) {

    public ScoreUpdatedEvent {
        Asserts.withContext(getClass())
            .notNull(score,"Score should not be null");
    }
}
