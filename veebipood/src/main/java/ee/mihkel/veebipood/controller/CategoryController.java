package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Category;
import ee.mihkel.veebipood.exception.IdMisuseException;
import ee.mihkel.veebipood.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) throws IdMisuseException {
        if (category.getId() != null) {
            throw new IdMisuseException("Cannot add with ID!");
        }
        categoryRepository.save(category);
        return ResponseEntity.status(201).body(categoryRepository.findAll());
    }

    @DeleteMapping("")
    public ResponseEntity<List<Category>> deleteCategory(@RequestParam Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PutMapping("")
    public ResponseEntity<List<Category>> editCategory(@RequestBody Category category) throws IdMisuseException {
        if (category.getId() == null) {
            throw new IdMisuseException("Cannot edit without ID!");
        }
        categoryRepository.save(category);
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    //2x või rohkem parameetrit: RequestParam localhost:8080/categories?id=1
    //Täpselt üks asi: PathVariable localhost:8080/categories/1
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryRepository.findById(id).orElseThrow());
    }
}
