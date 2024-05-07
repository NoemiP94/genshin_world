package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceDAO extends JpaRepository<Place, UUID> {
    Optional<Place> findByName(String name);

}
