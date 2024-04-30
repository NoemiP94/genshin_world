package noemi.genshin_world.security;


import noemi.genshin_world.entities.User;
import noemi.genshin_world.exceptions.UnauthorizedException;
import noemi.genshin_world.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new UnauthorizedException("Please, insert a valid bearer token!");
        } else {
            String token = authHeader.substring(7); // delete first 7 characters of token -> "Bearer "
            jwtTools.verifyToken(token);
            String id = jwtTools.extractIdFromToken(token);
            User currentUser = userService.findById(UUID.fromString(id));
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        String servletPath = request.getServletPath();
        return servletPath.startsWith("/auth/");
    }
}