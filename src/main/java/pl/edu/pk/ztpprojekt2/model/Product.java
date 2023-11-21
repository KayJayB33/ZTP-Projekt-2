package pl.edu.pk.ztpprojekt2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        @NotNull(message = "Name may not be null")
        @NotBlank(message = "Name may not be blank")
        @NotEmpty(message = "Name may not be empty")
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        private String name;

        @Column(nullable = false, columnDefinition = "TEXT")
        @NotNull(message = "Description may not be null")
        @NotBlank(message = "Description may not be blank")
        @NotEmpty(message = "Description may not be empty")
        private String description;

        @Column(nullable = false)
        @NotNull(message = "Price may not be null")
        @Positive
        private BigDecimal price;
        @Column(name = "available_quantity", nullable = false)
        @NotNull(message = "Available quantity may not be null")
        @PositiveOrZero
        private Integer availableQuantity;

        public Product() {
        }

        public Product(Long id, String name, String description, BigDecimal price, Integer availableQuantity) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.price = price;
                this.availableQuantity = availableQuantity;
        }

        public Long getId() {
                return id;
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

        public Integer getAvailableQuantity() {
                return availableQuantity;
        }

        public void setAvailableQuantity(Integer availableQuantity) {
                this.availableQuantity = availableQuantity;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Product product = (Product) o;
                return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(availableQuantity, product.availableQuantity);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, name, description, price, availableQuantity);
        }

        @Override
        public String toString() {
                return "Product{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", price=" + price +
                        ", availableQuantity=" + availableQuantity +
                        '}';
        }
}
