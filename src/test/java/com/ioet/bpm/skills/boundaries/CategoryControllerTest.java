package com.ioet.bpm.skills.boundaries;

import com.ioet.bpm.skills.domain.Category;
import com.ioet.bpm.skills.domain.Skill;
import com.ioet.bpm.skills.repositories.CategoryRepository;
import com.ioet.bpm.skills.repositories.SkillRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
    @Mock
    private SkillRepository skillRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    public void categoriesAreListedUsingTheRepository() throws Exception {

        ResponseEntity<Iterable> categories = categoryController.getAllCategories();

        Assert.assertEquals(HttpStatus.OK, categories.getStatusCode());
        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void whenACategoryIsCreatedTheNewCategoryIsReturned() throws Exception {
        Category categoryToCreate = Mockito.mock(Category.class);
        Category categoryCreated = Mockito.mock(Category.class);
        Optional<Category> category = Optional.of(Mockito.mock(Category.class));
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(category);
        Mockito.when(categoryToCreate.getId()).thenReturn("1");
        Mockito.when(categoryRepository.save(categoryToCreate)).thenReturn(categoryCreated);

        ResponseEntity<?> skillCreatedResponse = categoryController.createCategory(categoryToCreate);

        Assert.assertEquals(categoryCreated, skillCreatedResponse.getBody());
        Assert.assertEquals(HttpStatus.CREATED, skillCreatedResponse.getStatusCode());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(categoryToCreate);
    }

    @Test
    public void findCategoryById() {
        Category categoryToFind = Mockito.mock(Category.class);
        categoryController.getCategory(categoryToFind.getId());
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(categoryToFind.getId());
    }

    @Test
    public void theBodyContainsTheCategoryFromTheRepository() {
        String categoryIdToFind = "id";
        Category categoryFound = Mockito.mock(Category.class);
        Mockito.when(categoryRepository.findById(categoryIdToFind)).thenReturn(Optional.of(categoryFound));

        ResponseEntity<Category> existingCategoryResponse = categoryController.getCategory(categoryIdToFind);

        assertEquals(categoryFound, existingCategoryResponse.getBody());
        assertEquals(HttpStatus.OK, existingCategoryResponse.getStatusCode());
    }

    @Test
    public void deleteCategory() {
        Category categoryToDelete = Mockito.mock(Category.class);
        Mockito.when(categoryRepository.findById(categoryToDelete.getId())).thenReturn(Optional.of(categoryToDelete));

        ResponseEntity<?> deletedCategoryResponse = categoryController.deleteCategory(categoryToDelete.getId());

        assertNull(deletedCategoryResponse.getBody());
        assertEquals(HttpStatus.NO_CONTENT, deletedCategoryResponse.getStatusCode());
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(categoryToDelete);
    }
}
