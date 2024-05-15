package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Goal;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.goal.GoalDTO;
import noemi.genshin_world.payloads.goal.GoalResponseDTO;
import noemi.genshin_world.services.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/goal")
public class GoalController {
    @Autowired
    private GoalService goalService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public GoalResponseDTO createGoal(@Validated @RequestBody GoalDTO goal, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Goal newGoal = goalService.saveGoal(goal);
            return new GoalResponseDTO(newGoal.getId());
        }
    }

    @GetMapping("/getall")
    public Page<Goal> getGoal(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "12") int size,
                                      @RequestParam(defaultValue = "id") String orderBy){
        return goalService.findAllGoals(page, size, orderBy);
    }

    @GetMapping("/detail/{id}")
    public Goal getGoalById(@PathVariable UUID id){
        return goalService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Goal getGoalByIdAndUpdate(@PathVariable UUID id, @RequestBody GoalDTO newBody){
        return goalService.findByIdAndUpdate(id, newBody);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getGoalByIdAndDelete(@PathVariable UUID id){
        goalService.findByIdAndDelete(id);
    }

    @GetMapping("/detail/name/{name}")
    public Goal getGoalByName(@PathVariable String name){
        return goalService.findByName(name);
    }
}
