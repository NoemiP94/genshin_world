package noemi.genshin_world.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Character {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String voice;
    private String birthday;
    private String affiliate;
    @Enumerated(EnumType.STRING)
    private VisionType vision;
    //A character has one constellation
    @OneToOne(mappedBy = "character")
    private Constellation constellation;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    //A character has many talents
    @OneToMany(mappedBy = "character_id")
    private List<Talent> talentList;
    //many-to-many with artifactSet
    @ManyToMany(mappedBy = "characterList")
    @JoinTable(name = "character_name",
                joinColumns = @JoinColumn(name = "character_id"),
                inverseJoinColumns = @JoinColumn(name = "artifactSet_id"))
    private List<ArtifactSet> artifactSetList;
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    //many-to-many with weapon
    @ManyToMany(mappedBy = "characterList")
    @JoinTable(name = "character_name",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id"))
    private List<Weapon> weaponList;
    //A character has one region
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region_id;
    //A character has many materials
    @ManyToMany(mappedBy = "characterList")
    @JoinTable(name = "character_name",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> ascensionMaterials;


}
