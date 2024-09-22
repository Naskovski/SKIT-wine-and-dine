package mk.ukim.finki.diansvinarii.service;

import mk.ukim.finki.diansvinarii.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    public User findById(Long Id);
}
