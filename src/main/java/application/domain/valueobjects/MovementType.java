package application.domain.valueobjects;

public enum MovementType {

    INCOMING("Stock added to a warehouse, e.g. a new shipment received."),
    RESERVATION("Stock temporarily held for a pending order, not yet sold."),
    SALE_OUTGOING("Stock permanently removed because an order was completed."),
    ADJUSTMENT("Manual correction of stock quantity, e.g. an inventory count fix."),
    RETURN_RESTOCK("Stock returned to the warehouse after an approved product return.");

    private final String description;

    MovementType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}