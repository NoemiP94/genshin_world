package noemi.genshin_world.payloads.degree;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record DegreeDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotNull(message = "This field cannot be null")
        int level,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotNull(message = "This field cannot be null")
        UUID constellation_id
) {
}
