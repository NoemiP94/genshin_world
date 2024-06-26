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
            throw new UnauthorizedException("Please insert a valid bearer token!");
        } else {
            String token = authHeader.substring(7);
            jwtTools.verifyToken(token);
            String id = jwtTools.extractIdFromToken(token);
            User currentUser = userService.findById(UUID.fromString(id));
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        String servletPath = request.getServletPath();
        return servletPath.equals("/auth/login") || servletPath.equals("/auth/register")
                || servletPath.equals("/material/getall") || servletPath.startsWith("/material/detail")
                || servletPath.equals("/artifactset/getall") || servletPath.startsWith("/artifactset/detail")
                || servletPath.equals("/piece/getall") || servletPath.startsWith("/piece/detail")
                || servletPath.equals("/region/getall") || servletPath.startsWith("/region/detail")
                || servletPath.equals("/place/getall") || servletPath.startsWith("/place/detail")
                || servletPath.equals("/domain/getall") || servletPath.startsWith("/domain/detail")
                || servletPath.equals("/weapon/getall") || servletPath.startsWith("/weapon/detail")
                || servletPath.equals("/character/getall") || servletPath.startsWith("/character/detail")
                || servletPath.equals("/constellation/getall") || servletPath.startsWith("/constellation/detail")
                || servletPath.equals("/degree/getall") || servletPath.startsWith("/degree/detail")
                || servletPath.equals("/talent/getall") || servletPath.startsWith("/talent/detail")
                || servletPath.equals("/enemy/getall") || servletPath.startsWith("/enemy/detail")
                || servletPath.equals("/maingoal/getall") || servletPath.startsWith("/maingoal/detail")
                || servletPath.equals("/goal/getall") || servletPath.startsWith("/goal/detail")
                || servletPath.equals("/blogpost/getall") || servletPath.startsWith("/blogpost/detail");
    }
}
