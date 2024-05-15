package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Degree;
import noemi.genshin_world.entities.Talent;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.degree.DegreeDTO;
import noemi.genshin_world.payloads.degree.DegreeResponseDTO;
import noemi.genshin_world.payloads.talent.TalentDTO;
import noemi.genshin_world.payloads.talent.TalentResponseDTO;
import noemi.genshin_world.services.TalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/talent")
public class TalentController {
    @Autowired
    private TalentService talentService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public TalentResponseDTO createTalent(@Validated @RequestBody TalentDTO talent, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Talent newTalent = talentService.saveTalent(talent);
            return new TalentResponseDTO(newTalent.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Talent> getTalent(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  @RequestParam(defaultValue = "id") String orderBy){
        return talentService.findAllTalents(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Talent getTalentById(@PathVariable UUID id){
        return talentService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Talent getTalentByIdAndUpdate(@PathVariable UUID id, @RequestBody TalentDTO newBody){
        return talentService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getTalentByIdAndDelete(@PathVariable UUID id){
        talentService.findByIdAndDelete(id);
    }

    @PostMapping("/{talentId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addMaterialToTalent(@PathVariable UUID talentId, @PathVariable UUID materialId){
        talentService.addMaterialToTalent(talentId, materialId);
        return ResponseEntity.ok("Material added to Talent");
    }
    @DeleteMapping("/{talentId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeMaterialFromTalent(@PathVariable UUID talentId, @PathVariable UUID materialId ){
        talentService.removeMaterialFromTalent(talentId, materialId);
        return ResponseEntity.ok("Material removed from Talent");
    }
}
