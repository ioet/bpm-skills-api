package com.ioet.bpm.skills.repositories;

import com.ioet.bpm.skills.domain.Skill;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;


@EnableScan
public interface SkillRepository extends CrudRepository<Skill, String> {
    List<Skill> findByNameContaining (String name);
}
