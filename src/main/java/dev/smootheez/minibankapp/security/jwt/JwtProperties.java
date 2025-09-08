package dev.smootheez.minibankapp.security.jwt;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;
import org.springframework.validation.annotation.*;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    @NotBlank(message = "Secret key cannot be blank")
    @NotNull(message = "Secret key cannot be null")
    private String secretKey;
}
