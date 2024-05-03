package noemi.genshin_world.controller;

import noemi.genshin_world.entities.User;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.user.UserDTO;
import noemi.genshin_world.payloads.user.UserLoginDTO;
import noemi.genshin_world.payloads.user.UserLoginResponseDTO;
import noemi.genshin_world.payloads.user.UserResponseDTO;
import noemi.genshin_world.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO registerUser(@RequestBody @Validated UserDTO newUserBody, BindingResult validation) throws IOException{
        if(validation.hasErrors()){
            throw new BadRequestException("There are errors in the request!");
        } else {
            User newUser = authService.register(newUserBody);
            return new UserResponseDTO(newUser.getId());
        }
    }

    @PostMapping("/login")
    public UserLoginResponseDTO loginUser(@RequestBody UserLoginDTO body){
        Map<String, String> authResponse = authService.login(body);
        return new UserLoginResponseDTO(authResponse.get("token"), authResponse.get("role"));
    }

    //aggiungere update
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN', 'USER')")
    public User updateUser(@AuthenticationPrincipal User currentUser, @RequestBody UserDTO body){
        return authService.findByIdAndUpdate(currentUser.getId(), body);
    }


}
