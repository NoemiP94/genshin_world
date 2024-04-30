package noemi.genshin_world.payloads.place;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record PlaceDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        String image,
        String description,
        @NotNull(message = "This field cannot be null")
        UUID region_id
) {
}
