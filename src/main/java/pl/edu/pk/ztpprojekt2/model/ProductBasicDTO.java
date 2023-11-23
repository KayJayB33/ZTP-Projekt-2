package pl.edu.pk.ztpprojekt2.model;

import java.math.BigDecimal;

public class ProductBasicDTO {
    private String id;
    private String name;
    private BigDecimal price;
    private ProductState productState;

    public ProductBasicDTO() {
    }

    public ProductBasicDTO(String id, String name, BigDecimal price, ProductState productState) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productState = productState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductState getProductState() {
        return productState;
    }

    public void setProductState(ProductState productState) {
        this.productState = productState;
    }
}
