package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Region;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.region.RegionDTO;
import noemi.genshin_world.payloads.region.RegionResponseDTO;
import noemi.genshin_world.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
            Region newRegion = regionService.saveRegion(region);
            return new RegionResponseDTO(newRegion.getId());
        }
    }
    @GetMapping("/getall")
    public Page<Region> getRegions(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @RequestParam(defaultValue = "id") String orderBy){
        return regionService.findAllRegions(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Region getRegionById(@PathVariable UUID id){
        return regionService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Region getRegionByIdAndUpdate(@PathVariable UUID id, @RequestBody RegionDTO newBody){
        return regionService.findByIdAndUpdate(id, newBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getRegionByIdAndDelete(@PathVariable UUID id){
        regionService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Region getRegionByName(@PathVariable String name){
        return regionService.findByName(name);
    }

    @GetMapping("/detail/vision/{visionType}")
    public Page<Region> getRegionByVisionType(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sort, @PathVariable VisionType visionType){
        return regionService.findByVisionType(page, size, sort, visionType);
    }
}
