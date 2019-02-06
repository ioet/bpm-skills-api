package com.ioet.bpm.skills.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@DynamoDBTable(tableName = "skills_skill_person")
public class SkillPerson {

    @Id
    @DynamoDBHashKey
    @DynamoDBAttribute
    private String skillId;

    @DynamoDBAttribute
    private String personId;

}
