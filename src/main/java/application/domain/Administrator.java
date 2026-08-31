package application.domain;

import application.domain.valueobjects.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Administrator extends User {

    private String adminLevel;
    private RoleType roleType;
}