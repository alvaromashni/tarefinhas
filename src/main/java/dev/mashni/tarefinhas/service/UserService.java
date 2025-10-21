package dev.mashni.tarefinhas.service;

import dev.mashni.tarefinhas.model.User;
import dev.mashni.tarefinhas.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.saveAndFlush(user);
    }

    public User getUserByEmail(String email){
        return (User) userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email não encontrado.")
        );
    }

    public void deleteUserByEmail(String email){
        userRepository.deleteByEmail(email);
    }

    public void updateUserByEmail(String email, User user){
        User userEntity = getUserByEmail(email);
        User userUpdated = user.builder()
                .email(user.getEmail() != null ? user.getEmail() : userEntity.getEmail())
                .name(user.getName() != null ? user.getName() : userEntity.getName())
                .id(userEntity.getId())
                .build();
        userRepository.saveAndFlush(userUpdated);

    }

}
