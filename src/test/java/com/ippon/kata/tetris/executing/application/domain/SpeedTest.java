package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

import com.ippon.kata.tetris.shared.domain.Level;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SpeedTest {
  enum SpeedValues {
    ONE(1, 1000),
    TWO(2, 900),
    THREE(3, 800),
    FOUR(4, 700),
    FIVE(5, 600),
    SIX(6, 500),
    SEVEN(7, 400),
    EIGHT(8, 300),
    NINE(9, 200),
    TEN(10, 100);
    final int level;
    final int expectedSpeed;

    SpeedValues(int level, int expectedSpeed) {
      this.expectedSpeed = expectedSpeed;
      this.level = level;
    }
  }

  @ParameterizedTest
  @EnumSource(SpeedValues.class)
  void givenLevel_value_shouldReturn(SpeedValues speedValues) {

    final int speed = new Speed(new Level(speedValues.level)).value();

    then(speed).isEqualTo(speedValues.expectedSpeed);
  }
}
