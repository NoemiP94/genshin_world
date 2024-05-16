package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CharacterDAO extends JpaRepository<Character, UUID> {
    Optional<Character> findByName(String name);
    Page<Character> findByVisionType(VisionType visionType, Pageable pageable);
    Page<Character> findByWeaponType(WeaponType weaponType, Pageable pageable);

    //find all Characters with the same regionId
    @Query("SELECT c FROM Character c WHERE c.region_id = :region_id")
    Page<Character> findByRegionId(@Param("region_id") UUID regionId, Pageable pageable);
    //find all Characters with the same artifactSetId
    @Query("SELECT c FROM Character c WHERE c.artifactset_id = :artifactset_id")
    Page<Character> findByArtifactSetId(@Param("artifactset_id") UUID artifactsetId, Pageable pageable);
    //find all Characters with the same weaponId
    @Query("SELECT c FROM Character c WHERE c.weapon_id = :weapon_id")
    Page<Character> findByWeaponId(@Param("weapon_id") UUID weaponId, Pageable pageable);
    //find all Characters with the same materialId
    @Query("SELECT c FROM Character c WHERE c.material_id = :material_id")
    Page<Character> findByMaterialId(@Param("material_id") UUID materialId, Pageable pageable);
}
