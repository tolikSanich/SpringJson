package com.example.springjson.controller;

import com.example.springjson.dto.PersonDto;

import java.util.Collections;
import java.util.List;

public class PersonBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static PersonDto getDto() {
        PersonDto dto = new PersonDto();
        dto.setId("1");
        return dto;
    }
}
