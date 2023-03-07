package com.example.my_jwt.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Product extends BaseDocument {
    private String name;
    private String info;
}
