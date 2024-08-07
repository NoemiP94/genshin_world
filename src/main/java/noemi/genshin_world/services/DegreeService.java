package noemi.genshin_world.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Constellation;
import noemi.genshin_world.entities.Degree;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.degree.DegreeDTO;
import noemi.genshin_world.repositories.DegreeDAO;
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
public class DegreeService {
    @Autowired
    private DegreeDAO degreeDAO;
    @Autowired
    private ConstellationService constellationService;
    @Autowired
    private Cloudinary cloudinary;

    public Degree saveDegree(DegreeDTO body){
        Degree degree = new Degree();
        Constellation constellation = constellationService.findById(body.constellation_id());
        degree.setName(body.name());
        degree.setDescription(body.description());
        degree.setLevel(body.level());
        degree.setConstellation_id(constellation);
        return degreeDAO.save(degree);
    }

    public Page<Degree> findAllDegrees(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return degreeDAO.findAll(pageable);
    }

    public Degree findById(UUID id){
        return degreeDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Degree findByIdAndUpdate(UUID id, DegreeDTO newBody){
        Degree found = degreeDAO.findById(id).orElseThrow(()->new NotFoundException(id));
        Constellation newConstellation = constellationService.findById(newBody.constellation_id());
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setLevel(newBody.level());
        found.setConstellation_id(newConstellation);
        return degreeDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Degree found = degreeDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        degreeDAO.delete(found);
    }

    public Degree findByName(String name){
        return degreeDAO.findByName(name).orElseThrow(()-> new NotFoundException(name));
    }

    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Degree degree = degreeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        degree.setImage(url);
        degreeDAO.save(degree);
        return url;
    }
}
