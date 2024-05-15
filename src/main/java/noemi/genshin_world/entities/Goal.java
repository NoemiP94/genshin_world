package noemi.genshin_world.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"mainGoal_id"})
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
