package pl.edu.pk.ztpprojekt2.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pk.ztpprojekt2.model.Product;

import java.util.Optional;

@Repository
@JaversSpringDataAuditable
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
}
