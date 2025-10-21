package dev.mashni.tarefinhas.repository;

import dev.mashni.tarefinhas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<UserDetails> findByEmail(String username);
    Optional<UserDetails> deleteByEmail(String email);

}
