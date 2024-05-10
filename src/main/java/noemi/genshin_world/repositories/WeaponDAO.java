package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Weapon;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.entities.enums.WeaponType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WeaponDAO extends JpaRepository<Weapon, UUID> {
    Optional<Weapon> findByName(String name);
    Page<Weapon> findByStars(Stars stars, Pageable pageable);
    Page<Weapon> findByWeaponType(WeaponType weaponType, Pageable pageable);
}
