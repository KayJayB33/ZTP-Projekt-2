package pl.edu.pk.ztpprojekt2.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductState {
    OUT_OF_STOCK("out of stock"),
    AVAILABLE("available");

    private final String value;

    ProductState(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
