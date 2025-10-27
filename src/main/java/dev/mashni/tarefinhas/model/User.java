package dev.mashni.tarefinhas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users", indexes = {
        @Index(name = "uk_users_email", columnList = "email", unique = true)
})
@ToString(onlyExplicitlyIncluded = true)
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", unique = true, updatable = false, nullable = false)
    @Email
    @NotBlank
    @ToString.Include
    private String email;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @JsonIgnore
    @Column(name = "password", nullable = false)
    @ToString.Include
    private String password;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Builder
    public User(String email, String name, String password, UUID id) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.id = id;
    }


    // métodos do UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return !isDeleted; }

    // igualdade por chave natural ≥ "e-mail"

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return email != null && email.equals(other.email);
    }

    @Override
    public int hashCode(){
        return email == null ? 0 : email.hashCode();
    }

    // Sem setter público para email — mantém a chave estável
    // Se precisar de migração de email, criar um méto.do de domínio controlado.

}
