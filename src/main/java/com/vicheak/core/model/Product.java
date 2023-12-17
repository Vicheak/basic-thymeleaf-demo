package com.vicheak.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Category category;

}
