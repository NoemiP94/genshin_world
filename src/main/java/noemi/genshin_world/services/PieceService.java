package noemi.genshin_world.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import noemi.genshin_world.entities.ArtifactSet;
import noemi.genshin_world.entities.Piece;
import noemi.genshin_world.entities.enums.PieceType;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.exceptions.IllegalArgumentException;
import noemi.genshin_world.exceptions.NotFoundException;
import noemi.genshin_world.payloads.piece.PieceDTO;
import noemi.genshin_world.repositories.PieceDAO;
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
public class PieceService {
    @Autowired
    private PieceDAO pieceDAO;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ArtifactSetService artifactSetService;

    //save
    public Piece savePiece(PieceDTO body){
        ArtifactSet artifactSet = artifactSetService.findById(body.artifactSet_id());
        Piece piece = new Piece();

        piece.setName(body.name());
        piece.setDescription(body.description());
        piece.setArtifactSet_id(artifactSet);

        try{
            String pieceTypeString = body.pieceType();
            PieceType pieceType = PieceType.valueOf(pieceTypeString);
            piece.setPieceType(pieceType);
            String starsString = body.stars();
            Stars stars = Stars.valueOf(starsString);
            piece.setStars(stars);
        } catch(Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return pieceDAO.save(piece);

    }

    //uploadImage
    public String uploadImage(UUID id, MultipartFile file) throws IOException {
        Piece piece = pieceDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        piece.setImage(url);
        pieceDAO.save(piece);
        return url;
    }

    //findall
    public Page<Piece> findAllPieces(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return pieceDAO.findAll(pageable);
    }

    //findbyid
    public Piece findById(UUID id){
        return pieceDAO.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    //update
    public Piece findByIdAndUpdate(UUID id, PieceDTO newBody){
        Piece found = pieceDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        ArtifactSet newArtifactSet = artifactSetService.findById(newBody.artifactSet_id());
        found.setName(newBody.name());
        found.setDescription(newBody.description());
        found.setArtifactSet_id(newArtifactSet);
        try{
            String pieceTypeString = newBody.pieceType();
            PieceType pieceType = PieceType.valueOf(pieceTypeString);
            found.setPieceType(pieceType);
            String starsString = newBody.stars();
            Stars stars = Stars.valueOf(starsString);
            found.setStars(stars);
        } catch(Exception e){
            throw new IllegalArgumentException("Il dato fornito non è quello richiesto!");
        }
        return pieceDAO.save(found);
    }

    //delete
    public void findByIdAndDelete(UUID id){
        Piece found = pieceDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
        pieceDAO.delete(found);
    }

    //findbyname
    public Piece findByName(String name){
        return pieceDAO.findByName(name).orElseThrow(()-> new NotFoundException("Piece with name " + name + " not found!"));
    }

    //findbystars
    public Page<Piece> findByStars(int page, int size, String orderBy, Stars stars){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return pieceDAO.findByStars(stars, pageable);
    }
    //findbypiecetype
    public Page<Piece> findByPieceType(int page, int size, String orderBy, PieceType pieceType){
        try{
            Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
            return pieceDAO.findByPieceType(pieceType, pageable);
        } catch (Exception e){
            throw new NotFoundException("Pieces with pieceType " + pieceType + " not found!");
        }

    }

}
