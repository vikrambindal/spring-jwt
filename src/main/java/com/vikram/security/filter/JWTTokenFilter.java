package com.vikram.security.filter;

import com.vikram.domain.UserEntity;
import com.vikram.repository.UserEntityRepository;
import com.vikram.security.JWTUserDetails;
import com.vikram.service.JWTHelperService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TOKEN = "Bearer ";
    private final JWTHelperService jwtHelperService;
    private final UserEntityRepository userEntityRepository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_TOKEN)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authorizationHeader.substring(BEARER_TOKEN.length());

        final String email = jwtHelperService.verifyAndExtractClaims(jwtToken, Claims::getSubject);
        Optional<UserEntity> userEntityOpt = userEntityRepository.findByEmail(email);

        if (userEntityOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Invalid user %s", email));
        }

        JWTUserDetails userDetails = new JWTUserDetails(userEntityOpt.get());
        if (jwtHelperService.isTokenValid(jwtToken, userDetails)) {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
