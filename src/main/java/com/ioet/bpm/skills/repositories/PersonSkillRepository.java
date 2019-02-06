package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.PersonSkill;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PersonSkillRepository extends CrudRepository<PersonSkill, String> {

    Iterable<PersonSkill> findAllByPersonId(String peopleId);
}
