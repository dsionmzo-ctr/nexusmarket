package application.domain.valueobjects;

public enum RefundStatus {

    PENDING
    ("Refund approved but not yet processed."),
    PROCESSED
    ("Refund has been paid back to the buyer."),
    FAILED
    ("Refund attempt did not complete successfully.");

    private final String description;

    RefundStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}