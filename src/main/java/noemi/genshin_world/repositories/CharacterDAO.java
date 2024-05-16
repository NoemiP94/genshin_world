package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Character;
import noemi.genshin_world.entities.enums.Stars;
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
    Page<Character> findByStars(Stars stars, Pageable pageable);

    //find all Characters with the same regionId
    @Query("SELECT c FROM Character c JOIN Region r ON c.region_id = r.id WHERE r.id = :regionId")
    Page<Character> findByRegionId(@Param("regionId") UUID regionId, Pageable pageable);
    //find all Characters with the same artifactSetId
    @Query("SELECT c FROM Character c JOIN c.artifactSetList a WHERE a.id = :artifactSetId")
    Page<Character> findByArtifactset_Id(@Param("artifactSetId") UUID artifactSetId, Pageable pageable);
    //find all Characters with the same weaponId
    @Query("SELECT c FROM Character c JOIN c.favWeapons w WHERE w.id = :weaponId")
    Page<Character> findByWeaponId(@Param("weaponId") UUID weaponId, Pageable pageable);
    //find all Characters with the same materialId
    @Query("SELECT c FROM Character c JOIN c.ascensionMaterials m WHERE m.id = :materialId")
    Page<Character> findByMaterialId(@Param("materialId") UUID materialId, Pageable pageable);
}
