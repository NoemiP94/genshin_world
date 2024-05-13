package noemi.genshin_world.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"characterList"})
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
    //many-to-many with character
    @ManyToMany(mappedBy = "artifactSetList")
    private List<Character> characterList = new ArrayList<>();
}
