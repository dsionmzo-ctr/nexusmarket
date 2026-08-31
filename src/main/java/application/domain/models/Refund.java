package application.domain.models;

import application.domain.valueobjects.RefundStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Refund {

    private Long refundId;
    private Return relatedReturn;
    private Float amount;
    private LocalDateTime refundDate;
    private String method;
    private RefundStatus status;
}
