package application.domain.valueobjects;

public enum OrderStatus {

    PENDING_PAYMENT("Order created, awaiting payment confirmation."),
    PAID("Payment confirmed; order enters fulfillment."),
    SHIPPED("Order has left the warehouse for delivery."),
    DELIVERED("Order was delivered to the buyer; order is complete.");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
