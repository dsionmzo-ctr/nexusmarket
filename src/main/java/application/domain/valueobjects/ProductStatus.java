package application.domain.valueobjects;

public enum ProductStatus {

    PUBLISHED
    ("Visible to buyers and available for purchase."),
    SUSPENDED
    ("Temporarily hidden from the public catalog."),
    DISCONTINUED("Permanently retired; can no longer be sold.");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}