package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.Constellation;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.character.CharacterDTO;
import noemi.genshin_world.payloads.character.CharacterResponseDTO;
import noemi.genshin_world.payloads.constellation.ConstellationDTO;
import noemi.genshin_world.payloads.constellation.ConstellationResponseDTO;
import noemi.genshin_world.services.ConstellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/constellation")
public class ConstellationController {
    @Autowired
    private ConstellationService constellationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ConstellationResponseDTO createConstellation(@Validated @RequestBody ConstellationDTO constellation, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Constellation newCostellation = constellationService.saveConstellation(constellation);
            return new ConstellationResponseDTO(newCostellation.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Constellation> getConstellation(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "12") int size,
                                         @RequestParam(defaultValue = "id") String orderBy){
        return constellationService.findAllConstellations(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Constellation getConstellationById(@PathVariable UUID id){
        return constellationService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Constellation getConstellationByIdAndUpdate(@PathVariable UUID id, @RequestBody ConstellationDTO newBody){
        return constellationService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getConstellationByIdAndDelete(@PathVariable UUID id){
        constellationService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Constellation getConstellationByName(@PathVariable String name){
        return constellationService.findByName(name);
    }

}
