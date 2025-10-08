package dev.smootheez.minibankapp.config;

import dev.smootheez.minibankapp.service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException
    {
        String token = null; // The JWT token
        final String username; // The username of the user

        final String authorizationHeader = request.getHeader("Authorization"); // Get the authorization header from the request
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // If the authorization header is null or does not start with "Bearer ", then continue the filter chain
            token = authorizationHeader.substring(7); // Remove "Bearer " from the token
        }

        if (token == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtService.extractEmail(token); // Extract the username from the token

        SecurityContext context = SecurityContextHolder.getContext(); // Get the current security context
        if (username != null && context.getAuthentication() == null) { // If the username is not null and the authentication is null, then validate the token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Load the user details from the database
            if (jwtService.validateToken(token, userDetails)) { // Validate the token
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()); // Create a new authentication token with the user details
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Set the authentication details
                context.setAuthentication(authentication); // Set the authentication in the security context
            }
        }
        filterChain.doFilter(request, response); // Continue the filter chain
    }
}
