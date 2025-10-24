package dev.mashni.tarefinhas.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(@Email(message = "E-mail inválido") @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$") @NotEmpty(message = "Email é obrigatório") String email,
                           @NotEmpty(message = "Senha é obrigatória") String password) {

}
