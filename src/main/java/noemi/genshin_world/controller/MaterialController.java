package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Material;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.material.MaterialDTO;
import noemi.genshin_world.payloads.material.MaterialResponseDTO;
import noemi.genshin_world.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN','USER')")
    public MaterialResponseDTO createMaterial(@RequestBody @Validated MaterialDTO material, MultipartFile image, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Material newMaterial = materialService.saveMaterial(material, image);
            return new MaterialResponseDTO(newMaterial.getId());

        }
    }

    //findallmaterials
    @GetMapping("/getall")
    public Page<Material> getMaterials(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "12") int size,
                                       @RequestParam(defaultValue = "id") String orderBy){
        return materialService.findAllMaterials(page, size, orderBy);
    }
    //findbyid
    @GetMapping("/detail/{id}")
    public Material getMaterialById(@PathVariable UUID id){
        return materialService.findById(id);
    }
    //findbyname
    @GetMapping("/detail/{name}")
    public Material getMaterialByName(@PathVariable String name){
        return materialService.findByName(name);
    }
    //findByMaterialType
    @GetMapping("/detail/{type}")
    public Material getMaterialByType(@PathVariable String type){
        return materialService.findByMaterialType(type);
    }
    //findbyidandupdate
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN','USER')")
    public Material getMaterialByIdAndUpdate(@PathVariable UUID id, @RequestBody MaterialDTO newBody, MultipartFile image){
        return materialService.findByIdAndUpdate(id, newBody, image);
    }
    //findbyidanddelete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN','USER')")
    public void getMaterialByIdAndDelete(@PathVariable UUID id){
        materialService.findByIdAndDelete(id);
    }
}
