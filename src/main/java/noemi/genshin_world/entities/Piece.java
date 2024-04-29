package noemi.genshin_world.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.PieceType;
import noemi.genshin_world.entities.enums.Stars;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Piece {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private PieceType pieceType;
    @Enumerated(EnumType.STRING)
    private Stars stars;
    @ManyToOne
    @JoinColumn(name = "artifactSet_id")
    private ArtifactSet artifactSet_id;
}
