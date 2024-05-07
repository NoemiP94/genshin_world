package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import noemi.genshin_world.entities.Place;
import noemi.genshin_world.entities.Region;
import noemi.genshin_world.payloads.place.PlaceDTO;
import noemi.genshin_world.repositories.PlaceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
