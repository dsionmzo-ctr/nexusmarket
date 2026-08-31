package application.domain;

import application.domain.valueobjects.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private Long orderId;
    private Buyer buyer;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Float total;
}