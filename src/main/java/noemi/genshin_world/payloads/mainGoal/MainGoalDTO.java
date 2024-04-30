package noemi.genshin_world.payloads.mainGoal;

import javax.validation.constraints.NotEmpty;

public record MainGoalDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name
) {
}
