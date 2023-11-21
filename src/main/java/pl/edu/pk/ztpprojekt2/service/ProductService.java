package pl.edu.pk.ztpprojekt2.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pk.ztpprojekt2.controller.ProductRequest;
import pl.edu.pk.ztpprojekt2.exception.DuplicatedResourceException;
import pl.edu.pk.ztpprojekt2.exception.ResourceNotFoundException;
import pl.edu.pk.ztpprojekt2.model.Product;
import pl.edu.pk.ztpprojekt2.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id {%s} not found".formatted(id)));
    }

    public String addProduct(ProductRequest request) {
        if(productRepository.findByName(request.name()).isPresent()) {
            throw new DuplicatedResourceException("Product with name {%s} already exists".formatted(request.name()));
        }
        Product product = new Product(ObjectId.get().toString(),request.name(), request.description(), request.price(), request.availableQuantity());
        return productRepository.save(product).getId();
    }

    public void deleteProduct(String id) {
        getProduct(id);
        productRepository.deleteById(id);
    }

    public Product updateProduct(String id, ProductRequest request) {
        Product product = getProduct(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setAvailableQuantity(request.availableQuantity());
        return productRepository.save(product);
    }
}
