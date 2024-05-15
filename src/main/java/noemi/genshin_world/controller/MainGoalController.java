package noemi.genshin_world.controller;

import noemi.genshin_world.entities.MainGoal;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.mainGoal.MainGoalDTO;
import noemi.genshin_world.payloads.mainGoal.MainGoalResponseDTO;
import noemi.genshin_world.services.MainGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/maingoal")
public class MainGoalController {
    @Autowired
    private MainGoalService mainGoalService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public MainGoalResponseDTO createMainGoal(@Validated @RequestBody MainGoalDTO mainGoal, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            MainGoal newMainGoal = mainGoalService.saveMainGoal(mainGoal);
            return new MainGoalResponseDTO(newMainGoal.getId());
        }
    }

    @GetMapping("/getall")
    public Page<MainGoal> getMainGoal(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  @RequestParam(defaultValue = "id") String orderBy){
        return mainGoalService.findAllMainGoals(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public MainGoal getMainGoalById(@PathVariable UUID id){
        return mainGoalService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public MainGoal getMainGoalByIdAndUpdate(@PathVariable UUID id, @RequestBody MainGoalDTO newBody){
        return mainGoalService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getMainGoalByIdAndDelete(@PathVariable UUID id){
        mainGoalService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public MainGoal getMainGoalByName(@PathVariable String name){
        return mainGoalService.findByName(name);
    }

}
