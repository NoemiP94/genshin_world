package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Region;
import noemi.genshin_world.entities.enums.VisionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegionDAO extends JpaRepository<Region, UUID> {
    Optional<Region> findByName(String name);
    Page<Region> findByVisionType(VisionType visionType, Pageable pageable);
}
