package org.example.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserEmailId {
  private final String id;

  public UserEmailId(String id) {
    String emailRegex = "^(.+)@(.+)$";
    Matcher matcher = Pattern.compile(emailRegex).matcher(id);

    if (!matcher.matches()) throw new InvalidEmailException();

    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserEmailId userEmailId = (UserEmailId) o;
    return Objects.equals(id, userEmailId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
