package org.example.model;

import java.util.Objects;

public class Contributor {
    private String firstName;
    private String lastName;
    private String organization;
    private String role;

    public Contributor(String firstName, String lastName, String organization, String carName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.role = carName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contributor driver = (Contributor) o;
        return Objects.equals(firstName, driver.firstName) && Objects.equals(lastName, driver.lastName) && Objects.equals(organization, driver.organization) && Objects.equals(role, driver.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, organization, role);
    }
}
