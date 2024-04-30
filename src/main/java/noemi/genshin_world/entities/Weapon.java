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
public class Weapon {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String details;
    //many-to-many with material
    @ManyToMany(mappedBy = "weapons")
    private List<Material> materials = new ArrayList<>();
    //many-to-many with character
    @ManyToMany(mappedBy = "favWeapons")
    private List<Character> characterList = new ArrayList<>();
}
