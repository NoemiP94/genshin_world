package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.Enemy;
import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.Talent;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.enemy.EnemyDTO;
import noemi.genshin_world.payloads.enemy.EnemyResponseDTO;
import noemi.genshin_world.repositories.EnemyDAO;
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
public class EnemyService {
    @Autowired
    private EnemyDAO enemyDAO;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private Cloudinary cloudinary;

    public Enemy saveEnemy(EnemyDTO body){
        Enemy enemy = new Enemy();
        enemy.setName(body.name());
        enemy.setDescription(body.description());
        enemy.setCodeName(body.codeName());
        enemy.setPlace(body.place());
        return enemyDAO.save(enemy);
    }

    public void addMaterialToEnemy(UUID enemyId, UUID materialId){
        Enemy enemy = enemyDAO.findById(enemyId).orElseThrow(()-> new NotFoundException(enemyId));
        Material material = materialService.findById(materialId);
        enemy.getRewards().add(material);
        enemyDAO.save(enemy);
    }

    public void removeMaterialFromEnemy(UUID enemyId, UUID materialId) {
        Enemy enemy = enemyDAO.findById(enemyId).orElseThrow(() -> new NotFoundException(enemyId));
        Material material = materialService.findById(materialId);
        enemy.getRewards().remove(material);
        enemyDAO.save(enemy);
    }
    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Enemy enemy = enemyDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        enemy.setImage(url);
        enemyDAO.save(enemy);
        return url;
    }

    public Page<Enemy> findAllEnemies(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return enemyDAO.findAll(pageable);
    }

    public Enemy findById(UUID id){
        return enemyDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Enemy findByIdAndUpdate(UUID id, EnemyDTO newBody){
        Enemy found = enemyDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setCodeName(newBody.codeName());
        found.setPlace(newBody.place());
        return enemyDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Enemy found = enemyDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        enemyDAO.delete(found);
    }

    public Enemy findByName(String name){
        return enemyDAO.findByName(name).orElseThrow(()-> new NotFoundException(name));
    }
}
