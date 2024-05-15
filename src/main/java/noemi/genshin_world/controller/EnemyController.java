package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Enemy;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.enemy.EnemyDTO;
import noemi.genshin_world.payloads.enemy.EnemyResponseDTO;
import noemi.genshin_world.services.EnemyService;
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
@RequestMapping("/enemy")
public class EnemyController {
    @Autowired
    private EnemyService enemyService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public EnemyResponseDTO createEnemy(@Validated @RequestBody EnemyDTO enemy, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Enemy newEnemy = enemyService.saveEnemy(enemy);
            return new EnemyResponseDTO(newEnemy.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Enemy> getEnemies(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  @RequestParam(defaultValue = "id") String orderBy){
        return enemyService.findAllEnemies(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Enemy getEnemyById(@PathVariable UUID id){
        return enemyService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Enemy getEnemyByIdAndUpdate(@PathVariable UUID id, @RequestBody EnemyDTO newBody){
        return enemyService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getEnemyByIdAndDelete(@PathVariable UUID id){
        enemyService.findByIdAndDelete(id);
    }

    @PostMapping("/{enemyId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> addMaterialToEnemy(@PathVariable UUID enemyId, @PathVariable UUID materialId){
        enemyService.addMaterialToEnemy(enemyId, materialId);
        return ResponseEntity.ok("Material added to Enemy");
    }
    @DeleteMapping("/{enemyId}/material/{materialId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> removeMaterialFromEnemy(@PathVariable UUID enemyId, @PathVariable UUID materialId ){
        enemyService.removeMaterialFromEnemy(enemyId, materialId);
        return ResponseEntity.ok("Material removed from Enemy");
    }
    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return enemyService.uploadImage(id, body);
    }

    @GetMapping("/detail/name/{name}")
    public Enemy getEnemyByName(@PathVariable String name){
        return enemyService.findByName(name);
    }

}
