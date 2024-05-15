package noemi.genshin_world.services;

import noemi.genshin_world.entities.Goal;
import noemi.genshin_world.entities.MainGoal;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.goal.GoalDTO;
import noemi.genshin_world.repositories.GoalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GoalService {
    @Autowired
    private GoalDAO goalDAO;
    @Autowired
    private MainGoalService mainGoalService;

    public Goal saveGoal(GoalDTO body){
        Goal goal = new Goal();
        MainGoal mainGoal = mainGoalService.findById(body.mainGoal_id());
        goal.setName(body.name());
        goal.setDescription(body.description());
        goal.setMainGoal_id(mainGoal);
        return goalDAO.save(goal);
    }

    public Page<Goal> findAllGoals(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return goalDAO.findAll(pageable);
    }

    public Goal findById(UUID id){
        return goalDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Goal findByIdAndUpdate(UUID id, GoalDTO newBody){
        Goal found = goalDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        MainGoal newMainGoal = mainGoalService.findById(newBody.mainGoal_id());
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setMainGoal_id(newMainGoal);
        return goalDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Goal found = goalDAO.findById(id).orElseThrow(()->new NotFoundException(id));
        goalDAO.delete(found);
    }

    public Goal findByName(String name){
        return goalDAO.findByName(name).orElseThrow(()-> new NotFoundException(name));
    }
}
