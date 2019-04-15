package com.github.jander99.minimap.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sku_mappings")
public class SkuMapping {

    @Id
    private Long sku;

    private int gasCode;
}
