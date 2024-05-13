package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.*;
import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.character.CharacterDTO;
import noemi.genshin_world.repositories.CharacterDAO;
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
public class CharacterService {
    @Autowired
    private CharacterDAO characterDAO;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ArtifactSetService artifactSetService;
    @Autowired
    private WeaponService weaponService;
    @Autowired
    private MaterialService materialService;

    //save
    public Character saveCharacter(CharacterDTO body) {
        Character character = new Character();
        Region region = regionService.findById(body.region_id());
        character.setName(body.name());
        character.setVoice(body.voice());
        character.setBirthday(body.birthday());
        character.setAffiliate(body.affiliate());
        character.setDescription(body.description());
        character.setRegion_id(region);
        try {
            String visionTypeString = body.visionType();
            VisionType visionType = VisionType.valueOf(visionTypeString);
            character.setVisionType(visionType);
            String weaponTypeString = body.weaponType();
            WeaponType weaponType = WeaponType.valueOf(weaponTypeString);
            character.setWeaponType(weaponType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return characterDAO.save(character);
    }

    //addArtifactSet
    public void addArtifactSetToCharacter(UUID characterId, UUID artifactSetId) {
        Character character = characterDAO.findById(characterId).orElseThrow(() -> new NotFoundException(characterId));
        ArtifactSet artifactSet = artifactSetService.findById(artifactSetId);
        character.getArtifactSetList().add(artifactSet);
        characterDAO.save(character);
    }

    //removeArtifactSet
    public void removeArtifactSetFromCharacter(UUID characterId, UUID artifactSetId) {
        Character character = characterDAO.findById(characterId).orElseThrow(() -> new NotFoundException(characterId));
        ArtifactSet artifactSet = artifactSetService.findById(artifactSetId);
        character.getArtifactSetList().remove(artifactSet);
        characterDAO.save(character);
    }

    //addWeapon
    public void addWeaponToCharacter(UUID characterId, UUID weaponId) {
        Character character = characterDAO.findById(characterId).orElseThrow(() -> new NotFoundException(characterId));
        Weapon weapon = weaponService.findById(weaponId);
        character.getFavWeapons().add(weapon);
        characterDAO.save(character);
    }

    //removeWeapon
    public void removeWeaponFromCharacter(UUID characterId, UUID weaponId) {
        Character character = characterDAO.findById(characterId).orElseThrow(() -> new NotFoundException(characterId));
        Weapon weapon = weaponService.findById(weaponId);
        character.getFavWeapons().remove(weapon);
        characterDAO.save(character);
    }

    //addMaterial
    public void addMaterialToCharacter(UUID characterId, UUID materialId) {
        Character character = characterDAO.findById(characterId).orElseThrow(() -> new NotFoundException(characterId));
        Material material = materialService.findById(materialId);
        character.getAscensionMaterials().add(material);
        characterDAO.save(character);
    }

    //removeMaterial
    public void removeMaterialFromCharacter(UUID characterId, UUID materialId) {
        Character character = characterDAO.findById(characterId).orElseThrow(() -> new NotFoundException(characterId));
        Material material = materialService.findById(materialId);
        character.getAscensionMaterials().remove(material);
        characterDAO.save(character);
    }

    //uploadImage
    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Character character = characterDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        character.setImage(url);
        characterDAO.save(character);
        return url;
    }

    //findAll
    public Page<Character> findAllCharacter(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return characterDAO.findAll(pageable);
    }

    //findById
    public Character findById(UUID id) {
        return characterDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //update
    public Character findByIdAndUpdate(UUID id, CharacterDTO newBody) {
        Character found = characterDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        Region newRegion = regionService.findById(newBody.region_id());
        found.setName(newBody.name());
        found.setVoice(newBody.voice());
        found.setBirthday(newBody.birthday());
        found.setAffiliate(newBody.affiliate());
        found.setDescription(newBody.description());
        found.setRegion_id(newRegion);
        try {
            String visionTypeString = newBody.visionType();
            VisionType visionType = VisionType.valueOf(visionTypeString);
            found.setVisionType(visionType);
            String weaponTypeString = newBody.weaponType();
            WeaponType weaponType = WeaponType.valueOf(weaponTypeString);
            found.setWeaponType(weaponType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return characterDAO.save(found);
    }

    //delete
    public void findByIdAndDelete(UUID id) {
        Character character = characterDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        characterDAO.delete(character);
    }

    //findbyname
    public Character findByName(String name) {
        return characterDAO.findByName(name).orElseThrow(() -> new NotFoundException(name));
    }

    //findbyvisiontype
    public Page<Character> findByVisionType(int page, int size, String orderBy, VisionType visionType) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
            return characterDAO.findByVisionType(visionType, pageable);
        } catch (Exception e) {
            throw new NotFoundException("Character with visionType " + visionType + " not found!");
        }

    }

    //findbyweapontype
    public Page<Character> findByWeaponType(int page, int size, String orderBy, WeaponType weaponType) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
            return characterDAO.findByWeaponType(weaponType, pageable);
        } catch (Exception e) {
            throw new NotFoundException("Character with weaponType " + weaponType + " not found!");
        }

    }
}
