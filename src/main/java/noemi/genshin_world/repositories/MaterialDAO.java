package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Material;
import noemi.genshin_world.entities.enums.MaterialType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaterialDAO extends JpaRepository<Material, UUID> {
    Optional<Material> findByName(String name);

    //findByMaterialType
    Page<Material> findByMaterialType(MaterialType materialType, Pageable pageable);
}
