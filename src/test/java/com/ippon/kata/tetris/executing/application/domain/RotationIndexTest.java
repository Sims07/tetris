package com.ippon.kata.tetris.executing.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class RotationIndexTest {

  enum ExpectedRotationIndex {
    ONE(0, 1),
    TWO(1, 2),
    THREE(2, 3),
    FOUR(3, 0);
    private final int fromValue;
    private final int expectedValue;

    ExpectedRotationIndex(int fromValue, int expectedValue) {
      this.fromValue = fromValue;
      this.expectedValue = expectedValue;
    }
  }

  @Test
  void givenValueGreaterThanFour_constructor_shouldThrowException() {
    thenThrownBy(() -> new RotationIndex(4)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenValueLessThanZero_constructor_shouldThrowException() {
    thenThrownBy(() -> new RotationIndex(-1)).isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @EnumSource(ExpectedRotationIndex.class)
  void givenFrom_next_shouldBe(ExpectedRotationIndex expectedRotationIndex) {
    final RotationIndex rotationIndex = new RotationIndex(expectedRotationIndex.fromValue);

    then(rotationIndex.next().value()).isEqualTo(expectedRotationIndex.expectedValue);
  }
}
