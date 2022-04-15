package org.example.model;


import java.util.Objects;

public class Contributor {
    private final Long id;
    private final User user;
    private final String organization;
    private final Role role;

    public Contributor(Long id, User user, String organization, Role role) {
        this.id = id;
        this.user = user;
        this.organization = organization;
        this.role = role;
    }

    public boolean isDriver() {
        return getRole().equals(Role.DRIVER);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getOrganization() {
        return organization;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contributor that = (Contributor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
