package com.ioet.bpm.skills.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@DynamoDBTable(tableName = "skills_skill")
public class Skill {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @NotBlank
    @DynamoDBAttribute
    private String name;

    @NotBlank
    @DynamoDBAttribute
    private String label;

    @NotBlank
    @DynamoDBAttribute
    private Double businessValue;

    @NotBlank
    @DynamoDBAttribute
    private Double predictiveValue;

    @NotBlank
    @DynamoDBAttribute
    private String categoryId;
}
