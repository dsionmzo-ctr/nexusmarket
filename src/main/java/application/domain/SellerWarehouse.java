package application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class SellerWarehouse extends Warehouse {

    private String warehouseStatus;
}