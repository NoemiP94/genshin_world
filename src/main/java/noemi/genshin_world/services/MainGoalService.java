package noemi.genshin_world.services;

import noemi.genshin_world.entities.MainGoal;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.mainGoal.MainGoalDTO;
import noemi.genshin_world.repositories.MainGoalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MainGoalService {
    @Autowired
    private MainGoalDAO mainGoalDAO;

    public MainGoal saveMainGoal(MainGoalDTO body){
        MainGoal mainGoal = new MainGoal();
        mainGoal.setName(body.name());
        return mainGoalDAO.save(mainGoal);
    }

    public Page<MainGoal> findAllMainGoals(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return mainGoalDAO.findAll(pageable);
    }

    public MainGoal findById(UUID id){
        return mainGoalDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public MainGoal findByIdAndUpdate(UUID id, MainGoalDTO newBody){
        MainGoal found = mainGoalDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setName(newBody.name());
        return mainGoalDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        MainGoal found = mainGoalDAO.findById(id).orElseThrow(()->new NotFoundException(id));
        mainGoalDAO.delete(found);
    }

    public MainGoal findByName(String name){
        return mainGoalDAO.findByName(name).orElseThrow(()-> new NotFoundException(name));
    }
}
