package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CharacterDAO extends JpaRepository<Character, UUID> {
    Optional<Character> findByName(String name);
    Page<Character> findByVisionType(VisionType visionType, Pageable pageable);
    Page<Character> findByWeaponType(WeaponType weaponType, Pageable pageable);
    Page<Character> findByRegionId(UUID regionId, Pageable pageable);
    Page<Character> findByArtifactSetId(UUID artifactSetId, Pageable pageable);
    Page<Character> findByWeaponId(UUID weaponId, Pageable pageable);
    Page<Character> findByMaterialId(UUID materialId, Pageable pageable);
}
