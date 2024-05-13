package noemi.genshin_world.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.entities.enums.WeaponType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"characterList"})
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
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    @Enumerated(EnumType.STRING)
    private Stars stars;
    //many-to-many with material
    @ManyToMany
    @JoinTable(
            name="weapon_material",
            joinColumns = @JoinColumn(name = "weapon_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> materials = new ArrayList<>();
    //many-to-many with character
    @ManyToMany(mappedBy = "favWeapons")
    private List<Character> characterList = new ArrayList<>();
}