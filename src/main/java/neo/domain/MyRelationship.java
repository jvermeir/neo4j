package neo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@NoArgsConstructor
@RelationshipEntity(type="SOME_RELATIONSHIP")
public  class MyRelationship {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyRelationship.class);

    @JsonBackReference
    @StartNode
    private Thing thing;

    @EndNode
    private OtherThing otherThing;

    @Property
    private String name;

}
