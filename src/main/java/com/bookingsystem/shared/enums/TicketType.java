package com.bookingsystem.shared.enums;

/**
 * Enum representing the type of ticket.
 * VIP tickets have higher price and can include premium extras.
 * NORMAL tickets have standard price.
 */
public enum TicketType {
    VIP("VIP", 1.75),       // VIP multiplier applied on top of base price
    NORMAL("Normal", 1.0);  // Standard price, no multiplier

    private final String displayName;
    private final double priceMultiplier;

    TicketType(String displayName, double priceMultiplier) {
        this.displayName = displayName;
        this.priceMultiplier = priceMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
