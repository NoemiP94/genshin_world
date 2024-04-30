package noemi.genshin_world.payloads.enemy;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record EnemyDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotEmpty(message = "This field cannot be empty")
        String image,
        String codeName,
        String place,
        @NotNull(message = "This field cannot be null")
        List<UUID> rewards
) {
}
