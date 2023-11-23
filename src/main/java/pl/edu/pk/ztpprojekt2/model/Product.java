package pl.edu.pk.ztpprojekt2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("products")
@JsonPropertyOrder({"id", "name", "description", "price", "availableQuantity", "productStatus", "createdDate", "lastModifiedDate"})
public class Product extends BaseEntity {
        @NotNull(message = "Name may not be null")
        @NotBlank(message = "Name may not be blank")
        @NotEmpty(message = "Name may not be empty")
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        private String name;
        @NotNull(message = "Description may not be null")
        @NotBlank(message = "Description may not be blank")
        @NotEmpty(message = "Description may not be empty")
        private String description;
        @NotNull(message = "Price may not be null")
        @Positive
        private BigDecimal price;
        @NotNull(message = "Available quantity may not be null")
        @PositiveOrZero
        private int availableQuantity;

        public Product() {
        }

        public Product(String name, String description, BigDecimal price, int availableQuantity) {
                this.name = name;
                this.description = description;
                this.price = price;
                this.availableQuantity = availableQuantity;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }

        public int getAvailableQuantity() {
                return availableQuantity;
        }

        public void setAvailableQuantity(int availableQuantity) {
                this.availableQuantity = availableQuantity;
        }

        @JsonProperty("productState")
        public ProductState getProductState() {
                return this.availableQuantity > 0 ? ProductState.AVAILABLE : ProductState.OUT_OF_STOCK;
        }
}
