package pl.edu.pk.ztpprojekt2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pk.ztpprojekt2.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Product p set p.name = :name, p.description = :description, p.price = :price, p.availableQuantity = :availableQuantity where p.id = :id")
    void update(@Param("id") Long id, @Param("name") String name, @Param("description") String description, @Param("price") BigDecimal price, @Param("availableQuantity") Integer availableQuantity);

}
