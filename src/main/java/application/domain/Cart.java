package application.domain;

import application.domain.valueobjects.CartStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Cart {

    private Long cartId;
    private Buyer buyer;
    private LocalDateTime createdDate;
    private CartStatus status;
}
