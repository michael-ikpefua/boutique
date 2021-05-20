package com.michael.model;

import com.michael.utils.AutoDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
public class Product extends AutoDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title field is required")
    private String title;
    private String slug;
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "Description field is required")
    private String description;
    @NotEmpty(message = "Price field is required")
    private String price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
