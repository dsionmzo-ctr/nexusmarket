package application.domain.valueobjects;

public enum CartStatus {

    ACTIVE("The buyer is still adding or editing items."),
    CHECKED_OUT("The cart was confirmed and converted into an Order."),
    ABANDONED("The cart was left inactive without completing a purchase.");

    private final String description;

    CartStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}