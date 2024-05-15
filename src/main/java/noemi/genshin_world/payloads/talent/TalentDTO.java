package noemi.genshin_world.payloads.talent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record TalentDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        String info,

        List<UUID> materials_id,
        @NotNull(message = "This field cannot be null")
        UUID character_id
) {
}
