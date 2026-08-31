package application.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Buyer extends User {

    private String shippingAddress;
    private String additionalAddresses;
    private String commercialStatus;
}
