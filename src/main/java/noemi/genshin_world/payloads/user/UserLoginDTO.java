package noemi.genshin_world.payloads.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record UserLoginDTO(
        @NotNull(message = "This field cannot be null")
        @NotEmpty(message = "This field cannot be null")
        @Email
        String email,
        @NotNull(message = "This field cannot be null")
        @NotEmpty(message= "This field cannot be empty")
        @Size(min = 6, max = 16, message = "Password must be between 6 and 16 chars")
        String password) {
}
