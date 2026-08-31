package application.domain.enums;

public enum ReturnDecision {

    APPROVED("The return request was accepted; a refund can proceed."),
    REJECTED("The return request was denied; no refund will be issued.");

    private final String description;

    ReturnDecision(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}