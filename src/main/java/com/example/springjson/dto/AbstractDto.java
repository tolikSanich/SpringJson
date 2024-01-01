package com.example.springjson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractDto<E> {

    private LocalDateTime createAt;

    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;


}
