package noemi.genshin_world.payloads.goal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record GoalDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotNull(message = "This field cannot be null")
        UUID mainGoal_id
) {
}
