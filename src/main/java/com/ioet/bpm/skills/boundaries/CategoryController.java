package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Category;
import com.ioet.bpm.skills.repositories.CategoryRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/skills-categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @ApiOperation(value = "Return a list of all categories", response = Category.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categories successfully returned")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable> getAllCategories() {
        Iterable<Category> categories = this.categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @ApiOperation(value = "Return one category", response = Category.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category successfully returned")
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Category> getCategory(@PathVariable(value = "id") String categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.map(
                category -> new ResponseEntity<>(category, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new category", response = Category.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category successfully created")
    })
    @PostMapping(produces = "application/json")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category categoryCreated = categoryRepository.save(category);
        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Category successfully deleted"),
            @ApiResponse(code = 404, message = "The category to delete was not found")
    })
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") String categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.delete(category.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }  else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Update a category", response = Category.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Category successfully updated"),
            @ApiResponse(code = 404, message = "The category to update was not found")
    })
    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") String categoryId,
                                             @Valid @RequestBody Category categoryDetails) {

        Optional<Category> categoryFromDB = categoryRepository.findById(categoryId);
        if (categoryFromDB.isPresent()) {
            categoryDetails.setId(categoryFromDB.get().getId());
            Category savedCategory = categoryRepository.save(categoryDetails);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
