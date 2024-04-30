package noemi.genshin_world.payloads.weapon;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record WeaponDTO(
        @NotEmpty(message = "This message cannot be empty")
        String name,
        @NotEmpty(message = "This message cannot be empty")
        String image,
        @NotEmpty(message = "This message cannot be empty")
        String description,
        @NotEmpty(message = "This message cannot be empty")
        String details,
        @NotNull(message = "This field cannot be null")
        List<UUID> materials_id
) {
}
