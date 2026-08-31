package application.domain.models;

import application.domain.enums.ReturnDecision;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Return {

    private Long returnId;
    private Order order;
    private LocalDateTime requestDate;
    private String reason;
    private ReturnDecision decision;
}