package noemi.genshin_world.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import noemi.genshin_world.entities.enums.VisionType;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"characterList", "domainList"})
public class Region {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private VisionType visionType;
    @Column(columnDefinition = "TEXT")
    private String description;
    //A region has many places
    @OneToMany(mappedBy = "region_id")
    private List<Place> placeList;
    private String archon;
    //A region has many domains
    @OneToMany(mappedBy = "regionId")
    private List<Domain> domainList;
    //A region has many characters
    @OneToMany(mappedBy = "region_id")
    private List<Character> characterList;
    private String ideal;
    private String capital;
    private String festival;
    private String image;
}
