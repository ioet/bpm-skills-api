package com.ioet.bpm.skills.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Category {
    @NotBlank
    String name;
    @NotBlank
    Double businessValue;
    @NotBlank
    Double predictiveValue;
}
