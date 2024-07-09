package noemi.genshin_world.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.Stars;
import noemi.genshin_world.entities.enums.VisionType;
import noemi.genshin_world.entities.enums.WeaponType;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String engVoice;
    private String birthday;
    private String affiliate;
    @Enumerated(EnumType.STRING)
    private VisionType visionType;
    //A character has one constellation
    @OneToOne(mappedBy = "character")
    private Constellation constellation;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    @Enumerated(EnumType.STRING)
    private Stars stars;
    //A character has many talents
    @OneToMany(mappedBy = "character_id")
    private List<Talent> talentList;
    //many-to-many with artifactSet
    @ManyToMany
    @JoinTable(name = "character_artifactSet",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "artifactset_id"))
    private List<ArtifactSet> artifactSetList = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private WeaponType weaponType;
    //many-to-many with weapon
    @ManyToMany
    @JoinTable(name = "character_weapon",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id"))
    private List<Weapon> favWeapons = new ArrayList<>();
    //A character has one region
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region_id;
    //A character has many materials
    @ManyToMany
    @JoinTable(
            name="character_material",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> ascensionMaterials = new ArrayList<>();
    private String title;
    private double releaseVersion;
    private String japVoice;
    private String chinVoice;
    private String corVoice;
    private String specialDish;


}
