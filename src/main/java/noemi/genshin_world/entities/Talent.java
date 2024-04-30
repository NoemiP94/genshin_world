package noemi.genshin_world.entities;


import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    private List<Material> necessaryMaterials = new ArrayList<>();
    //many-to-one with character
    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character_id;
}
