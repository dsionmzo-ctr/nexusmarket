package application.domain.models;

import application.domain.valueobjects.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public abstract class User {

    private Long userId;
    private String fullName;
    private String documentNumber;
    private String email;
    private UserStatus status;
}