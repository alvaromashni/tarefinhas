package dev.mashni.tarefinhas.config;

import lombok.Builder;

@Builder
public record JWTUserData(String userId, String email) {
}
