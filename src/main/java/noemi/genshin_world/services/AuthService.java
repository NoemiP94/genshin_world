package noemi.genshin_world.services;

import noemi.genshin_world.entities.User;
import noemi.genshin_world.entities.enums.RoleType;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.exceptions.UnauthorizedException;
import noemi.genshin_world.payloads.user.UserDTO;
import noemi.genshin_world.payloads.user.UserLoginDTO;
import noemi.genshin_world.repositories.UserDAO;
import noemi.genshin_world.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public User register(UserDTO body) throws IOException{
        userDAO.findByEmail(body.email()).ifPresent(a->{
            throw new BadRequestException("User with email " + a.getEmail() + " already exists!");
        });
        User user = new User();
        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        user.setRole(RoleType.USER);
        return userDAO.save(user);
    }

    public Map<String, String> login(UserLoginDTO body){
        User user = userService.findByEmail(body.email());
        if(bcrypt.matches(body.password(), user.getPassword())){
            String role = String.valueOf(user.getRole());
            String accessToken = jwtTools.createToken(user);
            Map<String,String> response = new HashMap<>();
            response.put("role", role);
            response.put("token", accessToken);
            return response;
        } else {
            throw new UnauthorizedException("Something went wrong! Check if your credentials are right.");
        }
    }

    public User findByIdAndUpdate(UUID id, UserDTO newBody){
        User found = userService.findById(id);
        found.setName(newBody.name());
        found.setSurname(newBody.surname());
        found.setEmail(newBody.email());
        found.setPassword(bcrypt.encode(newBody.password()));
        return userDAO.save(found);
    }
}
