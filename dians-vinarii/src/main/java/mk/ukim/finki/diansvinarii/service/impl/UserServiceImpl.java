package mk.ukim.finki.diansvinarii.service.impl;


import lombok.RequiredArgsConstructor;

import mk.ukim.finki.diansvinarii.model.User;
import mk.ukim.finki.diansvinarii.repository.UserRepo;
import mk.ukim.finki.diansvinarii.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
            }



        };
    }

    public User findById(Long Id) {
        return userRepository.findById(Id).get();
    }


}