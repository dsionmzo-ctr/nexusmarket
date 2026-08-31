package application.domain;
import lombok.Getter; import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor

public class Seller extends User {
    private String sellerCode; }