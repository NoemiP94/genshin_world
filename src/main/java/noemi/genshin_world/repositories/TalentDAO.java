package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Talent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TalentDAO extends JpaRepository<Talent, UUID> {
}
