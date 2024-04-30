package noemi.genshin_world.payloads.exception;

import java.time.LocalDateTime;

public record ErrorDTO(String message, LocalDateTime timestamp) {
}
