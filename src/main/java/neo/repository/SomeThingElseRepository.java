package neo.repository;

import neo.domain.SomeThingElse;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeThingElseRepository extends Neo4jRepository<SomeThingElse, Long> {

}
