package com.michael.model;

import com.michael.utils.AutoDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends AutoDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name Field is Required")
    private String name;
    private String slug;

}
