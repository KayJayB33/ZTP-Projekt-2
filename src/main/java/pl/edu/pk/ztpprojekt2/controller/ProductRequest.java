package pl.edu.pk.ztpprojekt2.controller;

import java.math.BigDecimal;

public record ProductRequest(String name, String description, BigDecimal price, Integer availableQuantity) {

}
