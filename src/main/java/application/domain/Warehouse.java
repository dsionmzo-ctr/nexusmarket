package application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public abstract class Warehouse {

    private Long warehouseId;
    private String name;
    private String location;
    private Integer capacity;
}