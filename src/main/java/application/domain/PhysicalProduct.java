package application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class PhysicalProduct extends Product {

    private Double weight;
    private String dimensions;
    private String shippingType;
}
