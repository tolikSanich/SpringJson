package com.example.springjson.controller;

import com.example.springjson.controller.CustomUtils;
import com.example.springjson.controller.PersonController;
import com.example.springjson.dto.PersonDto;
import com.example.springjson.entities.Person;
import com.example.springjson.mapper.PersonMapper;
import com.example.springjson.service.PersonService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Transactional
public class PersonControllerTest {
    private static final String ENDPOINT_URL = "/api/person";

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .build();
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<PersonDto> page = new PageImpl<>(Collections.singletonList(PersonBuilder.getDto()));

        Mockito.when(personService.findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(page);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", Matchers.hasSize(1)));

        Mockito.verify(personService, Mockito.times(1)).findByCondition(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void getById() throws Exception {
        Mockito.when(personService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PersonBuilder.getDto());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));

//        Mockito.verify(personService, Mockito.times(1)).findById("1");
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(personService.save(ArgumentMatchers.any(PersonDto.class)))
                .thenReturn(PersonBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(PersonBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(personService, Mockito.times(1)).save(ArgumentMatchers.any(PersonDto.class));
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(personService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong()))
                .thenReturn(PersonBuilder.getDto());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(PersonBuilder.getDto())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(personService, Mockito.times(1))
                .update(ArgumentMatchers.any(PersonDto.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(personService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(personService).deleteById(ArgumentMatchers.anyLong());

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(PersonBuilder.getIds())))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(personService, Mockito.times(1))
                .deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(personService);
    }
}
