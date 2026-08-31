package application.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    private Long orderItemId;
    private Order order;
    private Product product;
    private Integer quantity;
    private Float unitPrice;
    private Float subtotal;
}