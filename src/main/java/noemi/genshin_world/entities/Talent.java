package noemi.genshin_world.entities;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"character_id"})
public class Talent {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String info;
    //many-to-many with material
    @ManyToMany
    @JoinTable(
            name="talent_material",
            joinColumns = @JoinColumn(name = "talent_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> necessaryMaterials = new ArrayList<>();
    //many-to-one with character
    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character_id;
    private String image;
}
