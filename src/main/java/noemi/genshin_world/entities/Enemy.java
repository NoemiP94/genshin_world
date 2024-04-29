package noemi.genshin_world.entities;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Enemy {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String image;
    private String codeName;
    private String place;
    //many to many con material
    @ManyToMany(mappedBy = "enemyList")
    @JoinTable(name = "enemy_name",
            joinColumns = @JoinColumn(name = "enemy_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> rewards;

}
