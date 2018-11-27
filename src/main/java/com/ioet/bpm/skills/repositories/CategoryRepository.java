package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.Category;
import com.ioet.bpm.skills.domain.Skill;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CategoryRepository extends CrudRepository<Category, String> {
}

