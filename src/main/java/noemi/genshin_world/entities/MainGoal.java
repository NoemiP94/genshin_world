package noemi.genshin_world.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MainGoal {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    //A mainGoal has many goals
    @OneToMany(mappedBy = "mainGoal_id")
    private List<Goal> goalList;
    private String image;
}
