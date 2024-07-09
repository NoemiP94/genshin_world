package noemi.genshin_world.controller;

import noemi.genshin_world.entities.ArtifactSet;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.artifactSet.ArtifactSetDTO;
import noemi.genshin_world.payloads.artifactSet.ArtifactSetResponseDTO;
import noemi.genshin_world.services.ArtifactSetService;
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
@RequestMapping("/artifactset")
public class ArtifactSetController {
    @Autowired
    private ArtifactSetService artifactSetService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ArtifactSetResponseDTO createArtifactSet(@Validated @RequestBody ArtifactSetDTO artifactSet, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            ArtifactSet newArtifactSet = artifactSetService.saveArtifactSet(artifactSet);
            return new ArtifactSetResponseDTO(newArtifactSet.getId());
        }
    }

    //findallartifactsets
    @GetMapping("/getall")
    public Page<ArtifactSet> getArtifactSets(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "12") int size,
                                             @RequestParam(defaultValue = "id") String orderBy){
        return artifactSetService.findAllArtifactSets(page, size, orderBy);
    }

    //findbyid
    @GetMapping("/detail/{id}")
    public ArtifactSet getArtifactSetById(@PathVariable UUID id){
        return artifactSetService.findById(id);
    }

    //findbyidandupdate
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ArtifactSet getArtifactSetByIdAndUpdate(@PathVariable UUID id, @RequestBody ArtifactSetDTO newBody){
        return artifactSetService.findByIdAndUpdate(id, newBody);
    }

    //findbyidanddelete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getArtifactSetByIdAndDelete(@PathVariable UUID id){
        artifactSetService.findByIdAndDelete(id);
    }

    //findbyname
    @GetMapping("/detail/name/{name}")
    public ArtifactSet getArtifactSetByName(@PathVariable String name){
        return artifactSetService.findByName(name);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return artifactSetService.uploadImage(id, body);
    }
}
