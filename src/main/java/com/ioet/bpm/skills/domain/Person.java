package com.ioet.bpm.skills.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private String id;
    private String name;
    private String authenticationIdentity;
    private String authenticationProvider;
    private String created;
    private String updated;
}
