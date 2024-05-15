package noemi.genshin_world.services;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.Talent;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.talent.TalentDTO;
import noemi.genshin_world.repositories.TalentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TalentService {
    @Autowired
    private TalentDAO talentDAO;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CharacterService characterService;

    public Talent saveTalent(TalentDTO body){
        Talent talent = new Talent();
        Character character = characterService.findById(body.character_id());
        talent.setName(body.name());
        talent.setInfo(body.info());
        talent.setCharacter_id(character);
        return talentDAO.save(talent);
    }

    public void addMaterialToTalent(UUID talentId, UUID materialId){
        Talent talent = talentDAO.findById(talentId).orElseThrow(()-> new NotFoundException(talentId));
        Material material = materialService.findById(materialId);
        talent.getNecessaryMaterials().add(material);
        talentDAO.save(talent);
    }

    public void removeMaterialFromTalent(UUID talentId, UUID materialId){
        Talent talent = talentDAO.findById(talentId).orElseThrow(()-> new NotFoundException(talentId));
        Material material = materialService.findById(materialId);
        talent.getNecessaryMaterials().remove(material);
        talentDAO.save(talent);
    }

    public Page<Talent> findAllTalents(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return talentDAO.findAll(pageable);
    }

    public Talent findById(UUID id){
        return talentDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Talent findByIdAndUpdate(UUID id, TalentDTO newBody){
        Talent found = talentDAO.findById(id).orElseThrow(()->new NotFoundException(id));
        Character newCharacter = characterService.findById(newBody.character_id());
        found.setName(newBody.name());
        found.setInfo(newBody.info());
        found.setCharacter_id(newCharacter);
        return talentDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Talent found = talentDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        talentDAO.delete(found);
    }
}
