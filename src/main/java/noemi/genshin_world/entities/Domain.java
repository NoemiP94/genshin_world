package noemi.genshin_world.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.DomainType;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Domain {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String place;
    @Enumerated(EnumType.STRING)
    private DomainType domainType;
    //material many-to-many
    @ManyToMany(mappedBy = "domains")
    private List<Material> rewards = new ArrayList<>();
    //region many-to-one
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region_id;


}
