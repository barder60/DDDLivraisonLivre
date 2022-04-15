package org.example.model;

import java.util.Objects;

public class User {
  private final String firstName;
  private final String lastName;
  private final UserId userId;
  private final String password;

  public User(String firstName, String lastName, UserId userId, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userId = userId;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public UserId getUserId() {
    return userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}
