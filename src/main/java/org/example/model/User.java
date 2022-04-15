package org.example.model;

import java.util.Objects;

public class User {
  private final String firstName;
  private final String lastName;
  private final UserEmailId userEmailId;
  private final String password;

  public User(String firstName, String lastName, UserEmailId userEmailId, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userEmailId = userEmailId;
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

  public UserEmailId getUserEmailId() {
    return userEmailId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userEmailId, user.userEmailId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userEmailId);
  }
}
