package noemi.genshin_world.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Degree {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private int level;
    @Column(columnDefinition = "TEXT")
    private String description;
    //A constellation have many degrees
    @ManyToOne
    @JoinColumn(name = "constellation_id")
    private Constellation constellation_id;
}
