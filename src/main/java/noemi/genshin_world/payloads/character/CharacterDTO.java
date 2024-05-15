package noemi.genshin_world.payloads.character;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CharacterDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        String voice,
        @NotEmpty(message = "This field cannot be empty")
        String birthday,
        String affiliate,
        String visionType,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotEmpty(message = "This field cannot be empty")
        String weaponType,

        String image,

        List<UUID> artifacts_id,

        List<UUID> weapons_id,

        UUID region_id,

        List<UUID> materials_id
) {
}
