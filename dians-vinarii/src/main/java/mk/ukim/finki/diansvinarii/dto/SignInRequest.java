package mk.ukim.finki.diansvinarii.dto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class SignInRequest {
    private String email;
    private String password;

}