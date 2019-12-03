package com.product.demo.data.models.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private String name;
    private Double currentPrice;
    private LocalDateTime lastUpdated;
}