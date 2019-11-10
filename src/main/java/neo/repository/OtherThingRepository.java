package neo.repository;

import neo.domain.OtherThing;
import neo.domain.Thing;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherThingRepository extends Neo4jRepository<OtherThing, Long> {

}
