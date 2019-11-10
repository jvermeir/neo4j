package neo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Getter
@Setter
@NoArgsConstructor
public class OtherThing {
    @Id @GeneratedValue
    private Long id;

    private String otherThingName;

    public String getOtherThingName() {return otherThingName;}
    public void setOtherThingName(String otherThingName) {this.otherThingName = otherThingName;}
    public OtherThing (String otherThingName) {this.otherThingName = otherThingName;}

}
