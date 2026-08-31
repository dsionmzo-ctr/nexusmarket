package application.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class DigitalProduct extends Product {

    private String fileFormat;
    private Double fileSize;
    private String downloadUrl;
}