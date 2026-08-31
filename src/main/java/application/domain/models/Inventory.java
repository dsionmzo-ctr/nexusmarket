package application.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    private Long inventoryId;
    private Product product;
    private Warehouse warehouse;
    private Integer quantity;
    private Integer reservedQuantity;
    private LocalDateTime lastUpdated;
}