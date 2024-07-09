package noemi.genshin_world.payloads.weapon;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record WeaponDTO(
        @NotEmpty(message = "This message cannot be empty")
        String name,
        String image,
        @NotEmpty(message = "This message cannot be empty")
        String description,
        @NotEmpty(message = "This message cannot be empty")
        String details,
        @NotEmpty(message = "This field cannot be empty")
        String weaponType,
        @NotEmpty(message = "This field cannot be empty")
        String stars,
        String origin
) {
}
