package com.ippon.kata.tetris.shared.secondary.spring.model;

import com.ippon.kata.tetris.executing.application.domain.Position;

public record PositionDTO(int x, int y) {
    public static PositionDTO from(Position position){
        return new PositionDTO(position.x(), position.y());
    }
}
