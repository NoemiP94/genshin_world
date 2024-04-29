package noemi.genshin_world.entities;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.MaterialType;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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
    @ManyToMany(mappedBy = "rewards")
    @JoinTable(name = "material_name",
                joinColumns = @JoinColumn(name = "material_id"),
                inverseJoinColumns = @JoinColumn(name = "enemy_id"))
    private List<Enemy> enemyList;
    //many-to-many with domain
    @ManyToMany(mappedBy = "rewards")
    @JoinTable(name = "material_name",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id"))
    private List<Domain> domainList;
    //many-to-many with weapon
    @ManyToMany(mappedBy = "necessaryWeaponMaterials")
    @JoinTable(name = "material_name",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id"))
    private List<Weapon> weaponList;
    //many-to-many with talent
    @ManyToMany(mappedBy = "necessaryMaterials")
    @JoinTable(name = "material_name",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "talent_id"))
    private List<Talent> talentList;
    //many-to-many with characters
    @ManyToMany(mappedBy = "ascensionMaterials")
    @JoinTable(name = "material_name",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<Character> characterList;


}
