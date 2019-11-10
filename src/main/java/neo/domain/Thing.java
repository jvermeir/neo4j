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
@NoArgsConstructor
@NodeEntity
public class Thing {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "SOME_RELATIONSHIP", direction= Relationship.INCOMING)
    private Set<MyRelationship> myRelationships;

    public Thing(String name) {
        this.name = name;
    }
}