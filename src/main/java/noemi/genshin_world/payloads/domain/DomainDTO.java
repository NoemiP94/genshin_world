package noemi.genshin_world.payloads.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record DomainDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        @NotEmpty(message = "This field cannot be empty")
        String place,
        @NotEmpty(message = "This field cannot be empty")
        String domainType,
        @NotNull(message = "This field cannot be null")
        UUID region_id
) {
}
