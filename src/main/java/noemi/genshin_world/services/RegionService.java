package noemi.genshin_world.services;

import noemi.genshin_world.entities.Region;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.region.RegionDTO;
import noemi.genshin_world.repositories.RegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegionService {
    @Autowired
    private RegionDAO regionDAO;

    public Region saveRegion(RegionDTO body){
        Region region = new Region();
        region.setName(body.name());
        region.setDescription(body.description());
        region.setArchon(body.archon());
        try {
            String visionTypeString = body.vision();
            VisionType visionType = VisionType.valueOf(visionTypeString);
            region.setVisionType(visionType);
        } catch (Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return regionDAO.save(region);
    }

    public Page<Region> findAllRegions(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return regionDAO.findAll(pageable);
    }

    public Region findById(UUID id){
        return regionDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Region findByIdAndUpdate(UUID id, RegionDTO newBody){
        Region found = regionDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setArchon(newBody.archon());
        try {
            String visionTypeString = newBody.vision();
            VisionType visionType = VisionType.valueOf(visionTypeString);
            found.setVisionType(visionType);
        } catch (Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return regionDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Region found = regionDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        regionDAO.delete(found);
    }

    //findByName
    public Region findByName(String name){
        return regionDAO.findByName(name).orElseThrow(()-> new NotFoundException("Region with name " + name + " not found!"));
    }

    //findByVisionType
    public Page<Region> findByVisionType(int page, int size, String orderBy, VisionType visionType){
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
            return regionDAO.findByVisionType(visionType, pageable);
        } catch (Exception e){
            throw new NotFoundException("Regions with vision " + visionType + " not found!");
        }
    }
}
