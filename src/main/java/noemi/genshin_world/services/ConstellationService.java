package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.Constellation;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.constellation.ConstellationDTO;
import noemi.genshin_world.repositories.ConstellationDAO;
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
public class ConstellationService {
    @Autowired
    private ConstellationDAO constellationDAO;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private Cloudinary cloudinary;
    public Constellation saveConstellation(ConstellationDTO body){
        Constellation constellation = new Constellation();
        Character character = characterService.findById(body.character_id());
        constellation.setName(body.name());
        constellation.setCharacter(character);
        return constellationDAO.save(constellation);
    }

    public Page<Constellation> findAllConstellations(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return constellationDAO.findAll(pageable);
    }

    public Constellation findById(UUID id){
        return constellationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Constellation findByIdAndUpdate(UUID id, ConstellationDTO newBody){
        Constellation found = constellationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        Character newCharacter = characterService.findById(newBody.character_id());
        found.setName(newBody.name());
        found.setCharacter(newCharacter);
        return constellationDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Constellation constellation = constellationDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        constellationDAO.delete(constellation);
    }

    public Constellation findByName(String name){
        return constellationDAO.findByName(name).orElseThrow(()-> new NotFoundException(name));
    }

    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Constellation constellation = constellationDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        constellation.setImage(url);
        constellationDAO.save(constellation);
        return url;
    }
}
