package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.character.CharacterDTO;
import noemi.genshin_world.payloads.character.CharacterResponseDTO;
import noemi.genshin_world.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/character")
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public CharacterResponseDTO createCharacter(@Validated @RequestBody CharacterDTO character, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Character newCharacter = characterService.saveCharacter(character);
            return new CharacterResponseDTO(newCharacter.getId());
        }
    }
}
