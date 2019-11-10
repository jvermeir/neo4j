package neo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Getter
@Setter
@NodeEntity
@NoArgsConstructor
public class SomeThingElse {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "SOME_RELATIONSHIP")
    private Set<OtherThing> myOtherThings;

    public SomeThingElse(String name) {
        this.name = name;
    }
}
