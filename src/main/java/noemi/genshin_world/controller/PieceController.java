package noemi.genshin_world.controller;

import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.Piece;
import noemi.genshin_world.entities.enums.MaterialType;
import noemi.genshin_world.entities.enums.PieceType;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.exceptions.BadRequestException;
import noemi.genshin_world.payloads.piece.PieceDTO;
import noemi.genshin_world.payloads.piece.PieceResponseDTO;
import noemi.genshin_world.services.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/piece")
public class PieceController {
    @Autowired
    private PieceService pieceService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public PieceResponseDTO createPiece(@Validated @RequestBody PieceDTO piece, BindingResult validation){
        if(validation.hasErrors()){
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in the request!");
        } else {
            Piece newPiece = pieceService.savePiece(piece);
            return new PieceResponseDTO(newPiece.getId());

        }
    }

    //findallpiece
    @GetMapping("/getall")
    public Page<Piece> getPieces(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "12") int size,
                                       @RequestParam(defaultValue = "id") String orderBy){
        return pieceService.findAllPieces(page, size, orderBy);
    }

    //findbyid
    @GetMapping("/detail/{id}")
    public Piece getPieceById(@PathVariable UUID id){
        return pieceService.findById(id);
    }

    //findbyidandupdate
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Piece getPieceByIdAndUpdate(@PathVariable UUID id, @RequestBody PieceDTO newBody){
        return pieceService.findByIdAndUpdate(id, newBody);
    }

    //findbyidanddelete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public void getPieceByIdAndDelete(@PathVariable UUID id){
        pieceService.findByIdAndDelete(id);
    }

    //uploadimage
    @PostMapping("/{id}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile body) throws IOException {
        return pieceService.uploadImage(id, body);
    }

    //findbyname
    @GetMapping("/detail/name/{name}")
    public Piece getPieceByName(@PathVariable String name){
        return pieceService.findByName(name);
    }

    //findbystars
    @GetMapping("/detail/stars/{stars}")
    public Page<Piece> getPieceByStars(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sort, @PathVariable Stars stars){
        return pieceService.findByStars(page, size, sort, stars);
    }

    //findbypiecetype
    @GetMapping("/detail/type/{pieceType}")
    public Page<Piece> getPieceByPieceType(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sort, @PathVariable PieceType pieceType){
        return pieceService.findByPieceType(page, size, sort, pieceType);
    }
}
