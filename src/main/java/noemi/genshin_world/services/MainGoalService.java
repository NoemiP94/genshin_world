package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.MainGoal;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.mainGoal.MainGoalDTO;
import noemi.genshin_world.repositories.MainGoalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class MainGoalService {
    @Autowired
    private MainGoalDAO mainGoalDAO;
    @Autowired
    private Cloudinary cloudinary;

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

    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        MainGoal mainGoal = mainGoalDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        mainGoal.setImage(url);
        mainGoalDAO.save(mainGoal);
        return url;
    }
}
