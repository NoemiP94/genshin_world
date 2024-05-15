package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnemyDAO extends JpaRepository<Enemy, UUID> {
    Optional<Enemy> findByName(String name);
}
