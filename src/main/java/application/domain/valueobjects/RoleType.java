package application.domain.valueobjects;

public enum RoleType {

    ADMINISTRATOR("Full administrative access: manages sellers, warehouses and users."),
    SUPERVISOR("Read-only oversight role: consults orders, inventory, reports and shipments."),
    LOGISTICS_OPERATOR("Executes and tracks shipment/dispatch operations on orders.");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}