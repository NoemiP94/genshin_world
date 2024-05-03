package noemi.genshin_world.payloads.material;

import javax.validation.constraints.NotEmpty;

public record MaterialDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,

        String image,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotEmpty(message = "This field cannot be empty")
        String materialType
) {
}
