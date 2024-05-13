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
        String vision,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotEmpty(message = "This field cannot be empty")
        String weaponType,
        @NotEmpty(message = "This field cannot be empty")
        String image,
        @NotNull(message = "This field cannot be null")
        List<UUID> artifacts_id,
        @NotNull(message = "This field cannot be null")
        List<UUID> weapons_id,
        @NotNull(message = "This field cannot be null")
        UUID region_id,
        @NotNull(message = "This field cannot be null")
        List<UUID> materials_id
) {
}
