package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.payloads.character.CharacterDTO;
import noemi.genshin_world.repositories.CharacterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
    @Autowired
    private CharacterDAO characterDAO;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArtifactSetService artifactSetService;

    //save
    public Character saveCharacter(CharacterDTO body){
        Character character = new Character();
        Region region = regionService.findById(body.region_id());
        character.setName(body.name());
        character.setVoice(body.voice());
        character.setBirthday(body.birthday());
        character.setAffiliate(body.affiliate());
        character.setDescription(body.description());
        character.setRegion_id(region);
        try{
            String visionTypeString = body.vision();
            VisionType visionType = VisionType.valueOf(visionTypeString);
            character.setVision(visionType);
            String weaponTypeString = body.weaponType();
            WeaponType weaponType = WeaponType.valueOf(weaponTypeString);
            character.setWeaponType(weaponType);
        } catch (Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return characterDAO.save(character);
    }

    //addArtifactSet

    //removeArtifactSet
    //addWeapon
    //removeWeapon
    //addMaterial
    //removeMaterial
    //uploadImage
    //findAll
    //findById
    //update
    //delete
    //custom filter
}
