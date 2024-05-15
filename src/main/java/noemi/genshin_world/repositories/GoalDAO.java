package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalDAO extends JpaRepository<Goal, UUID> {
    Optional<Goal> findByName(String name);
}
