package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.character.CharacterDTO;
import noemi.genshin_world.payloads.character.CharacterResponseDTO;
import noemi.genshin_world.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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
    //artifactSet
    @PostMapping("/{characterId}/artifactset/{artifactSetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addArtifactSetToCharacter(@PathVariable UUID characterId, @PathVariable UUID artifactSetId){
        characterService.addArtifactSetToCharacter(characterId, artifactSetId);
        return ResponseEntity.ok("ArtifactSet added to Character");
    }
    @DeleteMapping("/{characterId}/artifactset/{artifactSetId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeArtifactSetFromCharacter(@PathVariable UUID characterId, @PathVariable UUID artifactSetId){
        characterService.removeArtifactSetFromCharacter(characterId, artifactSetId);
        return ResponseEntity.ok("ArtifactSet removed from Character");
    }
    //weapon
    @PostMapping("/{characterId}/weapon/{weaponId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addWeaponToCharacter(@PathVariable UUID characterId, @PathVariable UUID weaponId){
        characterService.addWeaponToCharacter(characterId, weaponId);
        return ResponseEntity.ok("Weapon added to Character");
    }
    @DeleteMapping("/{characterId}/weapon/{weaponId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeWeaponFromCharacter(@PathVariable UUID characterId, @PathVariable UUID weaponId ){
        characterService.removeWeaponFromCharacter(characterId, weaponId);
        return ResponseEntity.ok("Weapon removed from Character");
    }
    //material
    @PostMapping("/{characterId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addMaterialToCharacter(@PathVariable UUID characterId, @PathVariable UUID materialId){
        characterService.addMaterialToCharacter(characterId, materialId);
        return ResponseEntity.ok("Material added to Character");
    }
    @DeleteMapping("/{characterId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeMaterialFromCharacter(@PathVariable UUID characterId, @PathVariable UUID materialId ){
        characterService.removeMaterialFromCharacter(characterId, materialId);
        return ResponseEntity.ok("Material removed from Character");
    }
    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return characterService.uploadImage(id, body);
    }
    @GetMapping("/getall")
    public Page<Character> getCharacters(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "12") int size,
                                     @RequestParam(defaultValue = "id") String orderBy){
        return characterService.findAllCharacter(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Character getCharacterById(@PathVariable UUID id){
        return characterService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Character getCharacterByIdAndUpdate(@PathVariable UUID id, @RequestBody CharacterDTO newBody){
        return characterService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getCharacterByIdAndDelete(@PathVariable UUID id){
        characterService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Character getCharacterByName(@PathVariable String name){
        return characterService.findByName(name);
    }

    @GetMapping("/detail/visionType/{visionType}")
    public Page<Character> getCharacterByVisionType(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sort, @PathVariable VisionType visionType){
        return characterService.findByVisionType(page, size,sort, visionType);
    }
    @GetMapping("/detail/stars/{stars}")
    public Page<Character> getCharacterByStars(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sort, @PathVariable Stars stars){
        return characterService.findByStars(page, size,sort, stars);
    }

    @GetMapping("/detail/weaponType/{weaponType}")
    public Page<Character> getCharacterByWeaponType(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "id") String sort, @PathVariable WeaponType weaponType){
        return characterService.findByWeaponType(page, size, sort, weaponType);
    }

    @GetMapping("/detail/region/{regionId}")
    public Page<Character> findByRegion(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sort,
                                        @PathVariable UUID regionId){
        return characterService.findByRegion(page,size,sort, regionId);
    }

    @GetMapping("/detail/artifact/{artifactSetId}")
    public Page<Character> findByArtifactSet(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sort,
                                        @PathVariable UUID artifactSetId){
        return characterService.findByArtifactSet(page,size,sort, artifactSetId);
    }

    @GetMapping("/detail/weapon/{weaponId}")
    public Page<Character> findByWeapon(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sort,
                                             @PathVariable UUID weaponId){
        return characterService.findByWeapon(page,size,sort, weaponId);
    }

    @GetMapping("/detail/material/{materialId}")
    public Page<Character> findByMaterial(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sort,
                                        @PathVariable UUID materialId){
        return characterService.findByMaterial(page,size,sort, materialId);
    }
}
