package noemi.genshin_world.payloads.constellation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ConstellationDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotNull(message = "This field cannot be null")
        UUID character_id,
        String image
) {
}
