package noemi.genshin_world.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"regionId"})
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
    @ManyToMany//(fetch = FetchType.EAGER, mappedBy = "domains", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "domain_material", // Nome della tabella di giunzione
            joinColumns = @JoinColumn(name = "domain_id"), // Colonna di dominio
            inverseJoinColumns = @JoinColumn(name = "material_id") // Colonna di materiale
    )
    private List<Material> materialList = new ArrayList<>();
    //region many-to-one
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region regionId;


}
