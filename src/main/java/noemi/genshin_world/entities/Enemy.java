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
    @ManyToMany
    @JoinTable(
            name="enemy_material",
            joinColumns = @JoinColumn(name = "enemy_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> rewards = new ArrayList<>();

}
