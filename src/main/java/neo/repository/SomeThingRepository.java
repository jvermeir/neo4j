package neo.repository;

import neo.domain.SomeThing;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeThingRepository extends Neo4jRepository<SomeThing, Long> {
    @Query("MATCH (someThing:SomeThing) WHERE someThing.name={name} RETURN someThing")
    SomeThing findByName(@Param("name") String name);
}
