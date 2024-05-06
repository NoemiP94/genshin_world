package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Region;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.region.RegionDTO;
import noemi.genshin_world.payloads.region.RegionResponseDTO;
import noemi.genshin_world.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public RegionResponseDTO createRegion(@Validated @RequestBody RegionDTO region, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Region newRegion = new Region();
            return new RegionResponseDTO(newRegion.getId());
        }
    }
}
