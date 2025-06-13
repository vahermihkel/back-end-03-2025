package ee.mihkel.veebipood.repository;

import ee.mihkel.veebipood.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

 // AINULT SELLISEID päringuid teha, mis tagastavad kas Product või List<Product>
    Page<Product> findByCategory_Id(Long id, Pageable pageable);

    Page<Product> findByNameContains(String searchField, Pageable pageable);
}
