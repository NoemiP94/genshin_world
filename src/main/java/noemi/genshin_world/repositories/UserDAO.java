package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDAO extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);
}
