package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.Category;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CategoryRepository extends CrudRepository<Category, String> {
}

