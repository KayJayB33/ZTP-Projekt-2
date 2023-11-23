package pl.edu.pk.ztpprojekt2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("products")
@JsonPropertyOrder({"id", "name", "description", "price", "availableQuantity", "productStatus", "createdDate", "lastModifiedDate"})
public class Product extends BaseEntity {

        private String name;
        private String description;
        private BigDecimal price;
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
