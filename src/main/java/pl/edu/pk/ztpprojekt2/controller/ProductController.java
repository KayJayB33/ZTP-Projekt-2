package pl.edu.pk.ztpprojekt2.controller;

import jakarta.validation.Valid;
import org.javers.core.Javers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.ztpprojekt2.model.Product;
import pl.edu.pk.ztpprojekt2.model.ProductBasicDTO;
import pl.edu.pk.ztpprojekt2.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final Javers javers;

    public ProductController(@Autowired ProductService productService, @Autowired Javers javers) {
        this.productService = productService;
        this.javers = javers;
    }

    @GetMapping
    @ResponseBody
    ResponseEntity<List<ProductBasicDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/history")
    @ResponseBody
    ResponseEntity<List<Product>> getProductHistoryById(@PathVariable("id") String id) {
        return new ResponseEntity<>(productService.getProductHistory(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @Valid @RequestBody ProductRequest request) {
        return new ResponseEntity<>(productService.updateProduct(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Succesfully deleted product with id {%s}".formatted(id), HttpStatus.OK);
    }
}
