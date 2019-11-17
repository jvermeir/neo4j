package neo;

import neo.domain.MyRelationship;
import neo.domain.OtherThing;
import neo.domain.Thing;
import neo.repository.OtherThingRepository;
import neo.repository.ThingRepository;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataNeo4jTest
@Testcontainers
public class ThingRepositoryIT {

    @Container
    private static final Neo4jContainer databaseServer = new Neo4jContainer();

    @TestConfiguration
    static class Config {

        @Bean
        public org.neo4j.ogm.config.Configuration configuration() {
            return new Configuration.Builder()
                    .uri(databaseServer.getBoltUrl())
                    .credentials("neo4j", databaseServer.getAdminPassword())
                    .build();
        }
    }

    private final ThingRepository thingRepository;

    private final OtherThingRepository otherThingRepository;

    @Autowired
    public ThingRepositoryIT(ThingRepository thingRepository, OtherThingRepository otherThingRepository) {
        this.thingRepository = thingRepository;
        this.otherThingRepository = otherThingRepository;
    }

    public void initializeDatabase() {
        Thing thing = new Thing("thing1");
        OtherThing otherThing = new OtherThing("otherthing1");
        MyRelationship myRelationship = new MyRelationship();
        myRelationship.setThing(thing);
        myRelationship.setOtherThing(otherThing);
        myRelationship.setName("myRelationshipName");
        thing.setMyRelationships(Set.of(myRelationship));

        otherThingRepository.save(otherThing);
        thingRepository.save(thing);
    }

    @Test
    @DirtiesContext
    public void testFindByName() {
        initializeDatabase();
        String thing1 = "thing1";
        List<Thing> things = new ArrayList<>();
        thingRepository.findAll().forEach(things::add);
        assertThat(things).hasSize(1);

        Thing result = thingRepository.findByName(thing1);
        assertNotNull(result);
        MyRelationship myRelationship = result.getMyRelationships().stream().findFirst().get();
        OtherThing otherThing = myRelationship.getOtherThing();
        assertEquals("otherthing1", otherThing.getOtherThingName());
    }

}
