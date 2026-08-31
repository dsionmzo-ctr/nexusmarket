package application.domain.models;

import application.domain.valueobjects.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public abstract class Product {

    private Long productId;
    private String name;
    private String description;
    private Float price;
    private ProductStatus status;
}