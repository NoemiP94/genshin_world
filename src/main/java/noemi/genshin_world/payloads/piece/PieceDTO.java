package noemi.genshin_world.payloads.piece;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record PieceDTO(
        @NotEmpty(message = "This field cannot be empty")
        String name,
        String image,
        @NotEmpty(message = "This field cannot be empty")
        String description,
        @NotEmpty(message = "This field cannot be empty")
        String pieceType,

        @NotNull(message = "This field cannot be null")
        UUID artifactSet_id
) {
}
