package com.ioet.bpm.skills.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Category {
    @NotBlank
    @DynamoDBHashKey
    private String Id;

    @NotBlank
    private String name;

    @NotBlank
    private Double businessValue;

    @NotBlank
    private Double predictiveValue;
}
