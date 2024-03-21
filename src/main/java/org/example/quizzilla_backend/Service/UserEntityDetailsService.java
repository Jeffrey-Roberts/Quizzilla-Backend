package org.example.quizzilla_backend.Service;

import org.example.quizzilla_backend.Model.UserEntity;
import org.example.quizzilla_backend.Model.UserEntityDetails;
import org.example.quizzilla_backend.Repository.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// https://hyperskill.org/learn/step/32430#userdetailsservice
@Service
public class UserEntityDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public UserEntityDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userEntityRepository.findUserEntityByUsernameIgnoreCase(username);
        return userEntity.map(UserEntityDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}