package neo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@RelationshipEntity(type="SOME_RELATIONSHIP")
public class MyRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @JsonBackReference
    @StartNode
    private Thing thing;

    @EndNode
    private OtherThing otherThing;

    @Property
    private String name;

}
