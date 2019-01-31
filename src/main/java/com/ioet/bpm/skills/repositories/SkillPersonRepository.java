package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.SkillPerson;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SkillPersonRepository extends CrudRepository<SkillPerson, String> {

    Iterable<SkillPerson> findAllBySkillId(String skillId);
}
