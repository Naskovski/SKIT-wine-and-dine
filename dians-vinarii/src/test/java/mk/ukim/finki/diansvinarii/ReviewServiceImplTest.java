package mk.ukim.finki.diansvinarii;

import mk.ukim.finki.diansvinarii.dto.SignUpRequest;
import mk.ukim.finki.diansvinarii.model.Review;
import mk.ukim.finki.diansvinarii.model.Role;
import mk.ukim.finki.diansvinarii.model.User;
import mk.ukim.finki.diansvinarii.model.Winery;
import mk.ukim.finki.diansvinarii.repository.ReviewRepository;
import mk.ukim.finki.diansvinarii.repository.UserRepo;
import mk.ukim.finki.diansvinarii.repository.WineryRepository;
import mk.ukim.finki.diansvinarii.service.impl.AuthenticationServiceImpl;
import mk.ukim.finki.diansvinarii.service.impl.ReviewServiceImpl;
import mk.ukim.finki.diansvinarii.service.impl.WineryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {


    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepo userRepository;

    @Mock
    private WineryRepository wineryRepository;

    @Mock
    private WineryServiceImpl wineryService;

    @InjectMocks
    private ReviewServiceImpl reviewService;





//    @Parameterized.Parameters
    public static Collection<Object[]> reviewParameters(){
        return Arrays.asList(new Object[][]{
                {5, "одлично", "T"}, //Test 1
                {5, "одлично место, топла препорака", "F"}, //Test 3
                {3, "одлично место, топла препорака", "T"}, //Test 7
                {3, "супер место, топла препорака", "F"} //Test 8
        });
    }

    @ParameterizedTest
    @MethodSource("reviewParameters")
    public void createReviewTestWithLogicCoverage(int score, String desc, String P){
        User user = new User(1L, "Filip", "filip@mail.com", "verySecurePAssword", Role.USER);
        Winery winery = new Winery(1L, "wineryOne", "072223305", "filip.com", 0.2, 0.3, "08:00", "18:00");


        if (P.equals("T")){
           Assertions.assertThrows(IllegalArgumentException.class, () -> reviewService.create(1L, score, desc, LocalDateTime.now(), 2L), "Inconsistent review");
        }
        else {
            Review expectedReview = reviewService.create(1L, score, desc, LocalDateTime.now(), 2L);
            when(reviewRepository.save(expectedReview)).thenReturn(expectedReview);

            Assertions.assertEquals(expectedReview, reviewService.create(1L, score, desc, LocalDateTime.now(), 2L));
        }
    }
}
