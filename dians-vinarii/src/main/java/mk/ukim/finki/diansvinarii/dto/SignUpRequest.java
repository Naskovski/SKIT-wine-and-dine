package mk.ukim.finki.diansvinarii.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String email;
    private String password;
}
