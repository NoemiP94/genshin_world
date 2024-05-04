package noemi.genshin_world.services;

import noemi.genshin_world.entities.ArtifactSet;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.artifactSet.ArtifactSetDTO;
import noemi.genshin_world.repositories.ArtifactSetDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArtifactSetService {
    @Autowired
    private ArtifactSetDAO artifactSetDAO;

    //save
    public ArtifactSet saveArtifactSet(ArtifactSetDTO body){
        ArtifactSet artifactSet = new ArtifactSet();
        artifactSet.setName(body.name());
        artifactSet.setDescription(body.description());
        return artifactSetDAO.save(artifactSet);
    }
    //findall
    public Page<ArtifactSet> findAllArtifactSets(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return artifactSetDAO.findAll(pageable);
    }
    //findbyid
    public ArtifactSet findById(UUID id){
        return artifactSetDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    //update
    public ArtifactSet findByIdAndUpdate(UUID id, ArtifactSetDTO newBody){
        ArtifactSet found = artifactSetDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        return artifactSetDAO.save(found);
    }
    //delete
    public void findByIdAndDelete(UUID id){
        ArtifactSet found = artifactSetDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        artifactSetDAO.delete(found);
    }
    //findbyname
    public ArtifactSet findByName(String name){
        return artifactSetDAO.findByName(name).orElseThrow(()-> new NotFoundException("ArtifactSet with name " + name + " not found!"));
    }
}
