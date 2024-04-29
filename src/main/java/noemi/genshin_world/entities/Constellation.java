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
public class Constellation {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    //A constellation has many degrees
    @OneToMany(mappedBy = "constellation_id")
    private List<Degree> degreesList;
    //One-to-One with character
    @OneToOne
    @JoinColumn(name = "character_id")
    private Character character;
}
