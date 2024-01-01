package com.example.springjson.controller;

import com.example.springjson.dto.PersonDto;
import com.example.springjson.entities.Person;
import com.example.springjson.exception.ResourceNotFoundException;
import com.example.springjson.mapper.PersonMapper;
import com.example.springjson.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/person")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api("person")
public class PersonController {
    private final PersonService personService;
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @RequestMapping("/json")
    public void json() throws IOException {
        URL url = this.getClass().getClassLoader().getResource("people.json");
        if (url!=null){
            File jsonFile = new File(url.getFile());
            ObjectMapper objectMapper = new ObjectMapper();
            List<PersonDto> people = objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
            personService.saveAll(people);
            logger.info("all record saved ");

        }
        else logger.warn("url is null");
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PersonDto personDto) {
        personService.save(personDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable("id") Long id) {
        PersonDto person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional.ofNullable(personService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data!");
            return new ResourceNotFoundException();
        });
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<PersonDto>> pageQuery(PersonDto personDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PersonDto> personPage = personService.findByCondition(personDto, pageable);
        return ResponseEntity.ok(personPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PersonDto personDto, @PathVariable("id") Long id) {
        personService.update(personDto, id);
        return ResponseEntity.ok().build();
    }
}
