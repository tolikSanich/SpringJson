package com.example.springjson.mapper;

import com.example.springjson.dto.PersonDto;
import com.example.springjson.entities.Person;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

//@Component
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDto, Person> {
}