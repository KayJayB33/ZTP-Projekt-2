package pl.edu.pk.ztpprojekt2;

import pl.edu.pk.ztpprojekt2.model.ProductState;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        int availableQuantity,
        ProductState productState,
        Instant createdDate,
        Instant lastModifiedDate
) {}
