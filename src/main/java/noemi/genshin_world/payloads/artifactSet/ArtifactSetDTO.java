package noemi.genshin_world.payloads.artifactSet;

import javax.validation.constraints.NotEmpty;

public record ArtifactSetDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        String description
) {
}
