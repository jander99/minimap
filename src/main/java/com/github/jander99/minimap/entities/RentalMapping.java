package com.github.jander99.minimap.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tool_rental_mappings")
public class RentalMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category;
    private String subcategory;
    private int gasCode;
}
