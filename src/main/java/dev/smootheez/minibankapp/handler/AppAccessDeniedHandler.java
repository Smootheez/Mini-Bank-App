package dev.smootheez.minibankapp.handler;

import com.fasterxml.jackson.databind.*;
import dev.smootheez.minibankapp.dto.response.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class AppAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        response.getWriter().write(objectMapper.writeValueAsString(ApiResponseEntity.build(HttpStatus.FORBIDDEN, "Access denied").getBody()));
    }
}
