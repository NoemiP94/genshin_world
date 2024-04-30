package noemi.genshin_world.payloads.region;

import javax.validation.constraints.NotEmpty;

public record RegionDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        String vision,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        String archon
) {
}
