package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PieceDAO extends JpaRepository<Piece, UUID> {
}
