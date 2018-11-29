package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Category;
import com.ioet.bpm.skills.repositories.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    public void categoriesAreListedUsingTheRepository() {

        ResponseEntity<Iterable> categories = categoryController.getAllCategories();

        Assert.assertEquals(HttpStatus.OK, categories.getStatusCode());
        verify(categoryRepository).findAll();
    }

    @Test
    public void whenACategoryIsCreatedTheNewCategoryIsReturned() {
        Category categoryToCreate = mock(Category.class);
        Category categoryCreated = mock(Category.class);
        when(categoryRepository.save(categoryToCreate)).thenReturn(categoryCreated);

        ResponseEntity<?> skillCreatedResponse = categoryController.createCategory(categoryToCreate);

        Assert.assertEquals(categoryCreated, skillCreatedResponse.getBody());
        Assert.assertEquals(HttpStatus.CREATED, skillCreatedResponse.getStatusCode());
        verify(categoryRepository).save(categoryToCreate);
    }

    @Test
    public void findCategoryById() {
        Category categoryToFind = mock(Category.class);
        categoryController.getCategory(categoryToFind.getId());
        verify(categoryRepository).findById(categoryToFind.getId());
    }

    @Test
    public void theBodyContainsTheCategoryFromTheRepository() {
        String categoryIdToFind = "id";
        Category categoryFound = mock(Category.class);
        when(categoryRepository.findById(categoryIdToFind)).thenReturn(Optional.of(categoryFound));

        ResponseEntity<Category> existingCategoryResponse = categoryController.getCategory(categoryIdToFind);

        assertEquals(categoryFound, existingCategoryResponse.getBody());
        assertEquals(HttpStatus.OK, existingCategoryResponse.getStatusCode());
    }

    @Test
    public void deleteCategory() {
        Category categoryToDelete = mock(Category.class);
        when(categoryRepository.findById(categoryToDelete.getId())).thenReturn(Optional.of(categoryToDelete));

        ResponseEntity<?> deletedCategoryResponse = categoryController.deleteCategory(categoryToDelete.getId());

        assertNull(deletedCategoryResponse.getBody());
        assertEquals(HttpStatus.NO_CONTENT, deletedCategoryResponse.getStatusCode());
        verify(categoryRepository).delete(categoryToDelete);
    }

    @Test
    public void updateCategory() {
        Category categoryUpdated = mock(Category.class);
        Optional<Category> categoryFound = Optional.of(mock(Category.class));

        String idCategoryToUpdate = "id";
        Category categoryToUpdate = mock(Category.class);

        when(categoryRepository.findById(idCategoryToUpdate)).thenReturn(categoryFound);
        when(categoryRepository.save(categoryToUpdate)).thenReturn(categoryUpdated);

        ResponseEntity<Category> updatedCategoryResponse =
                categoryController.updateCategory(idCategoryToUpdate, categoryToUpdate);

        assertEquals(categoryUpdated, updatedCategoryResponse.getBody());
        assertEquals(HttpStatus.OK, updatedCategoryResponse.getStatusCode());
        verify(categoryRepository).save(categoryToUpdate);
    }

    @Test
    public void whenUpdatingNotExistingCategoryThenReturn404() {
        String idCategoryToUpdate = "id";
        when(categoryRepository.findById(idCategoryToUpdate)).thenReturn(Optional.empty());

        ResponseEntity<Category> updatedCategoryResponse =
                categoryController.updateCategory(idCategoryToUpdate, mock(Category.class));

        assertEquals(HttpStatus.NOT_FOUND, updatedCategoryResponse.getStatusCode());
        verify(categoryRepository).findById(idCategoryToUpdate);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void whenDeletingNotExistingCategoryThenReturn404() {
        String idCategoryToDelete = "id";
        when(categoryRepository.findById(idCategoryToDelete)).thenReturn(Optional.empty());

        ResponseEntity deletedCategoryResponse =
                categoryController.deleteCategory(idCategoryToDelete);

        assertEquals(HttpStatus.NOT_FOUND, deletedCategoryResponse.getStatusCode());
        verify(categoryRepository, never()).delete(any());
        verify(categoryRepository).findById(idCategoryToDelete);
    }
}
