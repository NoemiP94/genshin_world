package noemi.genshin_world.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.MaterialType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"domains"})
public class Material {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;
    //many to many with enemy
    @ManyToMany
    @JoinTable(name = "enemy_material",
                joinColumns = @JoinColumn(name = "material_id"),
                inverseJoinColumns = @JoinColumn(name = "enemy_id"))
    private List<Enemy> enemies = new ArrayList<>();
    //many-to-many with domain
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "materialList", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Domain> domains = new ArrayList<>();
    //many-to-many with weapon
    @ManyToMany
    @JoinTable(name = "weapon_material",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id"))
    private List<Weapon> weapons = new ArrayList<>();
    //many-to-many with talent
    @ManyToMany
    @JoinTable(name = "talent_material",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "talent_id"))
    private List<Talent> talentList = new ArrayList<>();
    //many-to-many with characters
    @ManyToMany
    @JoinTable(name = "character_material",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<Character> characters = new ArrayList<>();


}
