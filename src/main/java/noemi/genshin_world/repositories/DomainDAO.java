package noemi.genshin_world.repositories;

import noemi.genshin_world.entities.Domain;
import noemi.genshin_world.entities.enums.DomainType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DomainDAO extends JpaRepository<Domain, UUID> {
    Optional<Domain> findByName(String name);
    Page<Domain> findByDomainType(DomainType domainType, Pageable pageable);
    Page<Domain> findByRegionId(UUID region_id, Pageable pageable);
}
