package pl.edu.pk.ztpprojekt2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id {%d} not found".formatted(id)));
    }

    public Long addProduct(Product product) {
        if(productRepository.findByName(product.getName()).isPresent()) {
            throw new DuplicatedResourceException("Product with name {%s} already exists".formatted(product.getName()));
        }
        return productRepository.save(product).getId();
    }

    public void deleteProduct(Long id) {
        getProduct(id);
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product newProduct) {
        productRepository.update(id,
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getPrice(),
                newProduct.getAvailableQuantity());
        return getProduct(id);
    }
}
