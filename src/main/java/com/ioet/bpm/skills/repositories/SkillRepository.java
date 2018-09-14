package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.Skill;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SkillRepository extends CrudRepository<Skill, String> {

}
