package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.enums.MaterialType;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.material.MaterialDTO;
import noemi.genshin_world.repositories.MaterialDAO;
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
public class MaterialService {
    @Autowired
    private MaterialDAO materialDAO;
    @Autowired
    private Cloudinary cloudinary;

    //save
    public Material saveMaterial(MaterialDTO body, MultipartFile imageFile){
        Material material = new Material();
        material.setName(body.name());
        material.setDescription(body.description());

        try{
            String imageUrl = uploadImage(imageFile);
            material.setImage(imageUrl);

            String materialTypeString = body.materialType();
            MaterialType materialType = MaterialType.valueOf(materialTypeString);
            material.setMaterialType(materialType);
        }catch(Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return materialDAO.save(material);
    }
    //findall
    public Page<Material> findAllMaterials(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return materialDAO.findAll(pageable);
    }

    //findbyid
    public Material findById(UUID id){
        return materialDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    //update
    public Material findByIdAndUpdate(UUID id, MaterialDTO newBody,MultipartFile newImageFile){
        Material found = materialDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setName(newBody.name());
        found.setDescription(newBody.description());

        try{
            String imageUrl = uploadImage(newImageFile);
            found.setImage(imageUrl);

            String materialTypeString = newBody.materialType();
            MaterialType materialType = MaterialType.valueOf(materialTypeString);
            found.setMaterialType(materialType);
        }catch(Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return materialDAO.save(found);
    }
    //delete
    public void findByIdAndDelete(UUID id){
        Material found = materialDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        materialDAO.delete(found);
    }

    //uploadImage
    public String uploadImage(MultipartFile file) throws IOException{
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }

    //findByName
    public Material findByName(String name){
        return materialDAO.findByName(name).orElseThrow(()-> new NotFoundException("Material with name " + name + " not found!"));
    }

    //findByMaterialType
    //controllare se funziona =D
    public Material findByMaterialType(String materialType){
        return materialDAO.findByMaterialType(materialType).orElseThrow(()-> new NotFoundException("Materials with type " + materialType + " not found!"));
    }
}
