package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Domain;
import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.Piece;
import noemi.genshin_world.entities.Weapon;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.entities.enums.WeaponType;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.weapon.WeaponDTO;
import noemi.genshin_world.repositories.WeaponDAO;
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
public class WeaponService {
    @Autowired
    private WeaponDAO weaponDAO;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private Cloudinary cloudinary;

    public Weapon saveWeapon(WeaponDTO body){
        Weapon weapon = new Weapon();
        weapon.setName(body.name());
        weapon.setDescription(body.description());
        weapon.setDetails(body.details());
        try{
            String weaponTypeString = body.weaponType();
            WeaponType weaponType = WeaponType.valueOf(weaponTypeString);
            weapon.setWeaponType(weaponType);
            String starsString = body.stars();
            Stars stars = Stars.valueOf(starsString);
            weapon.setStars(stars);
        } catch (Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return weaponDAO.save(weapon);
    }

    public void addMaterialToWeapon(UUID weaponId, UUID materialId){
        Weapon weapon = weaponDAO.findById(weaponId).orElseThrow(()-> new NotFoundException(weaponId));
        Material material = materialService.findById(materialId);
        weapon.getMaterials().add(material);
        weaponDAO.save(weapon);
    }

    public void deleteMaterialFromWeapon(UUID weaponId, UUID materialId){
        Weapon weapon = weaponDAO.findById(weaponId).orElseThrow(()-> new NotFoundException(weaponId));
        Material material = materialService.findById(materialId);
        weapon.getMaterials().remove(material);
        weaponDAO.save(weapon);
    }

    public Page<Weapon> findAllWeapons(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return weaponDAO.findAll(pageable);
    }

    public Weapon findById(UUID id){
        return weaponDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public Weapon findByIdAndUpdate(UUID id, WeaponDTO newBody){
        Weapon found = new Weapon();
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setDetails(newBody.details());
        try{
            String weaponTypeString = newBody.weaponType();
            WeaponType weaponType = WeaponType.valueOf(weaponTypeString);
            found.setWeaponType(weaponType);
            String starsString = newBody.stars();
            Stars stars = Stars.valueOf(starsString);
            found.setStars(stars);
        } catch (Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return weaponDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Weapon found = weaponDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        weaponDAO.delete(found);
    }

    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Weapon weapon = weaponDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        weapon.setImage(url);
        weaponDAO.save(weapon);
        return url;
    }

    public Weapon findByName(String name){
        return weaponDAO.findByName(name).orElseThrow(()-> new NotFoundException("Weapon with name " + name + " not found!"));
    }

    public Page<Weapon> findByStars(int page, int size, String orderBy, Stars stars){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return weaponDAO.findByStars(stars, pageable);
    }

}
