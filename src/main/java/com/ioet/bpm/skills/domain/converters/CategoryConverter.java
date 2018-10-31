package com.ioet.bpm.skills.domain.converters;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.ioet.bpm.skills.domain.Category;
import com.ioet.bpm.skills.domain.converters.exception.ConverterException;

public class CategoryConverter implements DynamoDBTypeConverter<String, Category> {

    @Override
    public String convert(Category category) {
        return ConverterHelper.fromObjectToString(category);
    }

    @Override
    public Category unconvert(String s) {
        return ConverterHelper.fromStringToObject(s);
    }

    static class ConverterHelper{
        static String fromObjectToString(Category category){
            String categoryAsString = null;
            try {
                if (category != null) {
                    categoryAsString = String.format("%s || %.1f || %.1f", category.getName(), category.getBusinessValue(),
                            category.getPredictiveValue());
                }
            } catch (Exception e) {
                throw new ConverterException(category, e);
            }
            return categoryAsString;
        }

        static Category fromStringToObject(String categoryAsString){
            Category category = null;
            try {
                if (categoryAsString != null && categoryAsString.length() != 0) {
                    String[] data = categoryAsString.split("\\|\\|");
                    String name = data[0].trim();
                    Double businessValue= Double.valueOf(data[1].trim());
                    Double predictiveValue=Double.valueOf(data[2].trim());
                    category = new Category(name,businessValue,predictiveValue);
                }
            } catch (Exception e) {
                throw new ConverterException(categoryAsString, e);
            }

            return category;
        }

    }
}

