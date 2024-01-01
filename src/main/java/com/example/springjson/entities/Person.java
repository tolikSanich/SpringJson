package com.example.springjson.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(catalog = "JsonBD", schema = "public", name = "person")
public class Person {
    @Id
    @GeneratedValue
    @Column(name = "pk", nullable = false)
    private Long pk;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;
    private String country;



}
