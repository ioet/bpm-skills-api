package com.ioet.bpm.skills.domain.converters;

import com.ioet.bpm.skills.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryConverterTest {

    Category category = new Category("id","catName", 1D,2D);
    String categoryAsString = "id || catName || 1.0 || 2.0";
    @Test
    public void convert() {
        assertEquals(categoryAsString, CategoryConverter.ConverterHelper.fromObjectToString(category));
    }

    @Test
    public void unconvert() {
        assertEquals(category,CategoryConverter.ConverterHelper.fromStringToObject(categoryAsString));

    }
}