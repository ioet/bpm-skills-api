package com.ioet.bpm.skills.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "skills_skill")
public class Skill {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @NotBlank
    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String label;

    @DynamoDBAttribute
    private Double businessValue;

    @DynamoDBAttribute
    private Double predictiveValue;

    @DynamoDBAttribute
    private String categoryId;
}
