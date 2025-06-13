package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.exception.IdMisuseException;
import ee.mihkel.veebipood.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@CrossOrigin("http://localhost:4200")
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    // Variant 2:

//    ProductRepository productRepository2;
//    public ProductController(ProductRepository productRepository) {
//        productRepository2 = productRepository;
//    }

    // localhost:8080   /products
    // api.max.ee       /products
    @GetMapping("/products") //size, page, sort,
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public List<Product> addProduct(@RequestBody Product product) throws IdMisuseException {
        if (product.getId() != null) {
            throw new IdMisuseException("Cannot add with ID!");
        }
        if (product.getPrice() < 0) {
            throw new RuntimeException("Cannot add with negative price!");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    // http://localhost:8080/products?id=1
    @DeleteMapping("/products")
    public List<Product> deleteProduct(@RequestParam Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @PutMapping("/products")
    public List<Product> editProduct(@RequestBody Product product) throws IdMisuseException {
        if (product.getId() == null) {
            throw new IdMisuseException("Cannot edit without ID!");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    @GetMapping("/product")
    public Product getProduct(@RequestParam Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PatchMapping("/products")
    public Product editProductField(@RequestParam Long id, String field, String value) {
        Product product = productRepository.findById(id).orElseThrow();
        switch (field) {
            case "name" -> product.setName(value);
            case "price" -> product.setPrice(Double.parseDouble(value));
            case "active" -> product.setActive(Boolean.parseBoolean(value));
            case "image" -> product.setImage(value);
        }
        return product;
    }

    @GetMapping("/products-by-category")
    public Page<Product> getProductsByCategory(@RequestParam Long categoryId, Pageable pageable) {
        if (categoryId == -1) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findByCategory_Id(categoryId, pageable);
    }

    @GetMapping("/search-from-products")
    public Page<Product> searchFromProducts(@RequestParam String searchField, Pageable pageable) {
        return productRepository.findByNameContains(searchField, pageable);
    }
}
