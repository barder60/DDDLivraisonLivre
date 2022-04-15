package org.example.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserEmailIdTest {
  @Test
  void when_user_id_is_invalid_email_should_throw_exception() {
    assertThatThrownBy(() -> new UserEmailId("notValidEmail")).isExactlyInstanceOf(InvalidEmailException.class);
  }

  @Test
  void when_user_id_is_valid_email_should_create_user_id() {
    var validId = "lucas@folhomee.fr";
    var userId = new UserEmailId(validId);

    assertThat(userId).isNotNull();
    assertThat(userId.getId()).isEqualTo(validId);
  }
}