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
public class ArtifactSet {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    //An artifactSet has many pieces
    @OneToMany(mappedBy = "artifactSet_id")
    private List<Piece> pieceList;
}
