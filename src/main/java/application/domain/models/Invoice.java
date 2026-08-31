package application.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    private Long invoiceId;
    private Order order;
    private LocalDateTime issueDate;
    private Float totalAmount;
    private Float taxAmount;
    private String paymentMethod;
}
