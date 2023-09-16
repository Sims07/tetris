package com.ippon.kata.tetris.gaming.application.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SettingsTest {

  public static final int INITIAL_LEVEL = 1;

  @Test
  void givenNoBaseLevel_constructor_shouldThrowException() {
    thenThrownBy(() -> new Settings(null)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenNegativeLevel_constructor_shouldThrowException() {
    thenThrownBy(() -> new Settings(new Level(-INITIAL_LEVEL)))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void givenSettings_increaseLevel_shouldReturnNewSettingsWithLevelIncreaseOfOne() {
    final Settings initialSetting = new Settings(new Level(INITIAL_LEVEL));

    final Settings settings = initialSetting.increaseLevel();

    then(settings).isNotNull();
    then(settings.level().value()).isEqualTo(INITIAL_LEVEL + 1);
  }
}
