package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CharacterDAO extends JpaRepository<Character, UUID> {
}
