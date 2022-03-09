package web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@Setter
@Getter
public class WebModelUser {
    private long id;

    @Email
    @NotBlank(message = "Cant be is empty")
    @Size(min = 8, max = 30)
    private String username;

    @NotBlank(message = "Cant be is empty")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "Cant be is empty")
    private String name;

    @NotBlank(message = "Cant be is empty")
    private String surname;

    @NotBlank(message = "Cant be is empty")
    @Positive
    @Max(90)
    private int age;

    private Set<String> roleList;
}
