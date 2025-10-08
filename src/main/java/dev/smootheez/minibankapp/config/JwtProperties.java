package dev.smootheez.minibankapp.config;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.validation.annotation.*;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    @NotBlank(message = "Secret cannot be blank")
    @NotNull(message = "Secret cannot be null")
    private String secret;

    @NotNull(message = "Expiration cannot be null")
    @Positive(message = "Expiration must be positive")
    private Long expiration;
}
