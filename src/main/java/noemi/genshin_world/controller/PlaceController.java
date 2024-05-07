package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Place;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.place.PlaceDTO;
import noemi.genshin_world.payloads.place.PlaceResponseDTO;
import noemi.genshin_world.payloads.region.RegionDTO;
import noemi.genshin_world.payloads.region.RegionResponseDTO;
import noemi.genshin_world.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public PlaceResponseDTO createPlace(@Validated @RequestBody PlaceDTO place, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Place newPlace = placeService.savePlace(place);
            return new PlaceResponseDTO(newPlace.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Place> getPlaces(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "12") int size,
                                   @RequestParam(defaultValue = "id") String orderBy){
        return placeService.findAllPlaces(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Place getPlaceById(@PathVariable UUID id){
        return placeService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Place getPlaceByIdAndUpdate(@PathVariable UUID id, @RequestBody PlaceDTO newBody){
        return placeService.findByIdAndUpdate(id, newBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getPlaceByIdAndDelete(@PathVariable UUID id){
        placeService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Place getPlaceByName(@PathVariable String name){
        return placeService.findByName(name);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return placeService.uploadImage(id, body);
    }
}
