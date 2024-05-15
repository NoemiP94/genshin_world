package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Degree;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.degree.DegreeDTO;
import noemi.genshin_world.payloads.degree.DegreeResponseDTO;
import noemi.genshin_world.services.DegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/degree")
public class DegreeController {
    @Autowired
    private DegreeService degreeService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public DegreeResponseDTO createDegree(@Validated @RequestBody DegreeDTO degree, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Degree newDegree = degreeService.saveDegree(degree);
            return new DegreeResponseDTO(newDegree.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Degree> getDegree(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "12") int size,
                                                @RequestParam(defaultValue = "id") String orderBy){
        return degreeService.findAllDegrees(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Degree getDegreeById(@PathVariable UUID id){
        return degreeService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Degree getDegreeByIdAndUpdate(@PathVariable UUID id, @RequestBody DegreeDTO newBody){
        return degreeService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getDegreeByIdAndDelete(@PathVariable UUID id){
        degreeService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Degree getDegreeByName(@PathVariable String name){
        return degreeService.findByName(name);
    }

}
