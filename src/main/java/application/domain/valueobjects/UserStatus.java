package application.domain.valueobjects;

public enum UserStatus {

    ACTIVE
    ("The user can log in and operate normally."),
    INACTIVE
    ("The user exists but is not currently operating."),
    BLOCKED
    ("The user has been blocked and cannot perform any operation.");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}