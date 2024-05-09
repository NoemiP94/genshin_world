package noemi.genshin_world.services;

import noemi.genshin_world.entities.*;
import noemi.genshin_world.entities.enums.DomainType;
import noemi.genshin_world.entities.enums.PieceType;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.domain.DomainDTO;
import noemi.genshin_world.payloads.piece.PieceDTO;
import noemi.genshin_world.payloads.place.PlaceDTO;
import noemi.genshin_world.repositories.DomainDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DomainService {
    @Autowired
    private DomainDAO domainDAO;
    @Autowired
    private RegionService regionService;
    @Autowired
    private MaterialService materialService;

    public Domain saveDomain(DomainDTO body){
        Domain domain = new Domain();
        Region region = regionService.findById(body.region_id());
        domain.setName(body.name());
        domain.setPlace(body.place());
        domain.setRegionId(region);
        try{
            String domainTypeString = body.domainType();
            DomainType domainType = DomainType.valueOf(domainTypeString);
            domain.setDomainType(domainType);
        } catch (Exception e ){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }

        return domainDAO.save(domain);
    }

    public void addMaterialToDomain(UUID domainId, UUID materialId){
        Domain domain = domainDAO.findById(domainId).orElseThrow(()-> new NotFoundException(domainId));
        Material material = materialService.findById(materialId);
        domain.getMaterialList().add(material);
        domainDAO.save(domain);
    }

    public Page<Domain> findAllDomains(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return domainDAO.findAll(pageable);
    }

    public Domain findById(UUID id){
        return domainDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public Domain findByIdAndUpdate(UUID id, DomainDTO newBody){
        Domain found = domainDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        Region newRegion = regionService.findById(newBody.region_id());
        found.setName(newBody.name());
        found.setPlace(newBody.place());
        found.setRegionId(newRegion);
        try{
            String domainTypeString = newBody.domainType();
            DomainType domainType = DomainType.valueOf(domainTypeString);
            found.setDomainType(domainType);
        } catch (Exception e ){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }

        return domainDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Domain found = domainDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        domainDAO.delete(found);
    }

    public Domain findByName(String name){
        return domainDAO.findByName(name).orElseThrow(()-> new NotFoundException("Domain with name " + name + " not found!"));
    }

    public Page<Domain> findByDomainType(int page, int size, String orderBy, DomainType domainType){
        try{
            Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
            return domainDAO.findByDomainType(domainType, pageable);
        } catch (Exception e){
            throw new NotFoundException("Domain with domainType " + domainType + " not found!");
        }

    }

    public Page<Domain> findByRegionId(int page, int size, String orderBy,UUID id){
        try{
            Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
            return domainDAO.findByRegionId(id, pageable);
        } catch (Exception e){
            throw new NotFoundException("Domain with regionId " + id + " not found!");
        }

    }
}
