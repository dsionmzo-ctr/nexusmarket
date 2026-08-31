package application.domain;

import application.domain.valueobjects.MovementType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InventoryMovement {

    private Long movementId;
    private Inventory inventory;
    private MovementType type;
    private Integer quantity;
    private LocalDateTime date;
    private String reason;
}