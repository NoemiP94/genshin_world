package noemi.genshin_world.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    //many-to-one with mainGoal
    @ManyToOne
    @JoinColumn(name = "mainGoal_id")
    private MainGoal mainGoal_id;
}
