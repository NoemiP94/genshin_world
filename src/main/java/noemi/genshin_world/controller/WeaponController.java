package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Weapon;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.entities.enums.WeaponType;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.weapon.WeaponDTO;
import noemi.genshin_world.payloads.weapon.WeaponResponseDTO;
import noemi.genshin_world.services.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/weapon")
public class WeaponController {
    @Autowired
    private WeaponService weaponService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public WeaponResponseDTO createWeapon(@Validated @RequestBody WeaponDTO weapon, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Weapon newWeapon = weaponService.saveWeapon(weapon);
            return new WeaponResponseDTO(newWeapon.getId());
        }
    }

    @PostMapping("/{weaponId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addMaterialToWeapon(@PathVariable UUID weaponId, @PathVariable UUID materialId){
        weaponService.addMaterialToWeapon(weaponId, materialId);
        return ResponseEntity.ok("Material added to Weapon");
    }
    @DeleteMapping("/{weaponId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeMaterialFromWeapon(@PathVariable UUID weaponId, @PathVariable UUID materialId){
        weaponService.deleteMaterialFromWeapon(weaponId, materialId);
        return ResponseEntity.ok("Material removed from Domain");
    }
    @GetMapping("/getall")
    public Page<Weapon> getWeapons(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "12") int size,
                                   @RequestParam(defaultValue = "id") String orderBy){
        return weaponService.findAllWeapons(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Weapon getWeaponById(@PathVariable UUID id){
        return weaponService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Weapon getWeaponByIdAndUpdate(@PathVariable UUID id, @RequestBody WeaponDTO newBody){
        return weaponService.findByIdAndUpdate(id, newBody);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getWeaponByIdAndDelete(@PathVariable UUID id){
        weaponService.findByIdAndDelete(id);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return weaponService.uploadImage(id, body);
    }

    @GetMapping("/detail/name/{name}")
    public Weapon getWeaponByName(@PathVariable String name){
        return weaponService.findByName(name);
    }

    @GetMapping("/detail/stars/{stars}")
    public Page<Weapon> getWeaponByStars(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sort, @PathVariable Stars stars){
        return weaponService.findByStars(page, size, sort, stars);
    }

    @GetMapping("/detail/type/{weaponType}")
    public Page<Weapon> getWeaponByWeaponType(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sort, @PathVariable WeaponType weaponType){
        return weaponService.findByWeaponType(page, size, sort, weaponType);
    }
}
