package noemi.genshin_world.entities;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    //many-to-many with character
    @ManyToMany(mappedBy = "artifactSetList")
    @JoinTable(name = "artifactSet_character",
                joinColumns = @JoinColumn(name = "artifactSet_id"),
                inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<Character> characterList;
}
