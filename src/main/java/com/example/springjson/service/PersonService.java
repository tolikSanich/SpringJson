package com.example.springjson.service;

import com.example.springjson.dto.PersonDto;
import com.example.springjson.entities.Person;
import com.example.springjson.exception.ResourceNotFoundException;
import com.example.springjson.mapper.PersonMapper;
import com.example.springjson.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@Transactional

public class PersonService {
    private final PersonRepository repository;
    @Autowired
    private PersonMapper personMapper;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public PersonDto save(PersonDto personDto) {
        Person entity = personMapper.toEntity(personDto);
        return personMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PersonDto findById(Long id) {
        try {
            return personMapper.toDto(repository.findById(id)
                    .orElseThrow(ResourceNotFoundException::new));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Page<PersonDto> findByCondition(PersonDto personDto, Pageable pageable) {
        Page<Person> entityPage = repository.findAll(pageable);
        List<Person> entities = entityPage.getContent();
        return new PageImpl<>(personMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PersonDto update(PersonDto personDto, Long id) {
        PersonDto data = findById(id);
        Person entity = personMapper.toEntity(personDto);
        BeanUtils.copyProperties(data, entity);
        return save(personMapper.toDto(entity));
    }

    public List<PersonDto> saveAll(List<PersonDto> personDtos) {
        List<Person> entityList = personMapper.toEntity(personDtos);
       return personMapper.toDto(repository.saveAll(entityList));
    }
}
