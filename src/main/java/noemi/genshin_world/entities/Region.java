package noemi.genshin_world.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.VisionType;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Region {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private VisionType vision;
    @Column(columnDefinition = "TEXT")
    private String description;
    //A region has many places
    @OneToMany(mappedBy = "region_id")
    private List<Place> placeList;
    private String archon;
    //A region has many domains
    @OneToMany(mappedBy = "region_id")
    private List<Domain> domainList;

}
