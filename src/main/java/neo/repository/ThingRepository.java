package neo.repository;

import neo.domain.OtherThing;
import neo.domain.Thing;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThingRepository extends Neo4jRepository<Thing, Long> {

//    @Query("MATCH (thing:Thing) WHERE thing.name={name} RETURN thing")
    @Query("MATCH (thing:Thing)-[c:SOME_RELATIONSHIP]-(otherThing:OtherThing) WHERE thing.name={name} RETURN thing, c, otherThing")
    Thing findByName(@Param("name") String name);

}
