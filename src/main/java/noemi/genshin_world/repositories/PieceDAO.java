package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Piece;
import noemi.genshin_world.entities.enums.PieceType;
import noemi.genshin_world.entities.enums.Stars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PieceDAO extends JpaRepository<Piece, UUID> {
    Optional<Piece> findByName(String name);


    Page<Piece> findByPieceType(PieceType pieceType, Pageable pageable);
}
