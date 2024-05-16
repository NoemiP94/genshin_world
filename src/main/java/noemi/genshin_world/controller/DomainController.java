package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Domain;
import noemi.genshin_world.entities.enums.DomainType;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.domain.DomainDTO;
import noemi.genshin_world.payloads.domain.DomainResponseDTO;
import noemi.genshin_world.services.DomainService;
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
@RequestMapping("/domain")
public class DomainController {
    @Autowired
    private DomainService domainService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public DomainResponseDTO createDomain(@Validated @RequestBody DomainDTO domain, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Domain newDomain = domainService.saveDomain(domain);
            return new DomainResponseDTO(newDomain.getId());
        }
    }

    @PostMapping("/{domainId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addMaterialToDomain(@PathVariable UUID domainId, @PathVariable UUID materialId){
        domainService.addMaterialToDomain(domainId, materialId);
        return ResponseEntity.ok("Material added to Domain");
    }
    @DeleteMapping("/{domainId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeMaterialFromDomain(@PathVariable UUID domainId, @PathVariable UUID materialId){
        domainService.deleteMaterialFromDomain(domainId, materialId);
        return ResponseEntity.ok("Material removed from Domain");
    }

    @GetMapping("/getall")
    public Page<Domain> getDomains(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @RequestParam(defaultValue = "id") String orderBy){
        return domainService.findAllDomains(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Domain getDomainById(@PathVariable UUID id){
        return domainService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Domain getDomainByIdAndUpdate(@PathVariable UUID id, @RequestBody DomainDTO newBody){
        return domainService.findByIdAndUpdate(id, newBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getDomainByIdAndDelete(@PathVariable UUID id){
        domainService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Domain getDomainByName(@PathVariable String name){
        return domainService.findByName(name);
    }

    @GetMapping("/detail/type/{domainType}")
    public Page<Domain> getDomainByDomainType(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sort, @PathVariable DomainType domainType){
        return domainService.findByDomainType(page, size, sort, domainType);
    }



}
