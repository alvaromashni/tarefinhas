package dev.mashni.tarefinhas.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty(message = "Nome obrigatorio") String name,
                                  @NotEmpty(message = "Email obrigatorio") String email,
                                  @NotEmpty(message = "Senha obrigatoria") String password) {
}

