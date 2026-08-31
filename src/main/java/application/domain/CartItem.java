package application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CartItem {

    private Long cartItemId;
    private Cart cart;
    private Product product;
    private Integer quantity;
    private Float unitPriceSnapshot;
}