package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.enums.MaterialType;
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

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public MaterialResponseDTO createMaterial(@Validated @RequestBody MaterialDTO material, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Material newMaterial = materialService.saveMaterial(material);
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

    //findbyidandupdate
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Material getMaterialByIdAndUpdate(@PathVariable UUID id, @RequestBody MaterialDTO newBody){
        return materialService.findByIdAndUpdate(id, newBody);
    }
    //findbyidanddelete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getMaterialByIdAndDelete(@PathVariable UUID id){
        materialService.findByIdAndDelete(id);
    }

    //uploadimage
    @PostMapping("/{id}/image")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException{
        return materialService.uploadImage(id, body);
    }

    //findbyname
    @GetMapping("/detail/name/{name}")
    public Material getMaterialByName(@PathVariable String name){
        return materialService.findByName(name);
    }
    //findByMaterialType
    @GetMapping("/detail/type/{materialType}")
    public Page<Material> getMaterialByType(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sort, @PathVariable MaterialType materialType){
        return materialService.findByMaterialType(page, size, sort, materialType);
    }
}
