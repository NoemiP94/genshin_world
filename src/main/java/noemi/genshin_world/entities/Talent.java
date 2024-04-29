package noemi.genshin_world.entities;


import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Talent {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String info;
    //many-to-many with material
    @ManyToMany(mappedBy = "talentList")
    @JoinTable(name = "talent_name",
            joinColumns = @JoinColumn(name = "talent_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> necessaryMaterials;
    //many-to-one with character
    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character_id;
}
