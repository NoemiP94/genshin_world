package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import noemi.genshin_world.entities.Place;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.place.PlaceDTO;
import noemi.genshin_world.repositories.PlaceDAO;
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
public class PlaceService {
    @Autowired
    private PlaceDAO placeDAO;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private RegionService regionService;

    public Place savePlace(PlaceDTO body){
        Place place = new Place();
        Region region = regionService.findById(body.region_id());
        place.setName(body.name());
        place.setDescription(body.description());
        place.setRegion_id(region);
        return placeDAO.save(place);
    }

    public Page<Place> findAllPlaces(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return placeDAO.findAll(pageable);
    }

    public Place findById(UUID id){
        return placeDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public Place findByIdAndUpdate(UUID id, PlaceDTO newBody){
        Place found = placeDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        Region newRegion = regionService.findById(newBody.region_id());
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setRegion_id(newRegion);
        return placeDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Place found = placeDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        placeDAO.delete(found);
    }

    //uploadImage
    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Place place = placeDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        place.setImage(url);
        placeDAO.save(place);
        return url;
    }

    //findbyname
    //findByName
    public Place findByName(String name){
        return placeDAO.findByName(name).orElseThrow(()-> new NotFoundException("Place with name " + name + " not found!"));
    }
}
