package noemi.genshin_world.payloads.blogpost;

import javax.validation.constraints.NotEmpty;


public record BlogpostDTO(
        @NotEmpty(message = "this field cannot be empty")
        String title,
        @NotEmpty(message = "this field cannot be empty")
        String content,
        String image
) {
}
