package neo;

import neo.domain.*;
import neo.repository.OtherThingRepository;
import neo.repository.SomeThingElseRepository;
import neo.repository.SomeThingRepository;
import neo.repository.ThingRepository;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataNeo4jTest
@Testcontainers
@Transactional
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
    private final SomeThingElseRepository someThingElseRepository;
    private final SomeThingRepository someThingRepository;
    private final OtherThingRepository otherThingRepository;

    @Autowired
    public ThingRepositoryIT(ThingRepository thingRepository,
                             OtherThingRepository otherThingRepository,
                             SomeThingRepository someThingRepository,
                             SomeThingElseRepository someThingElseRepository ) {
        this.thingRepository = thingRepository;
        this.otherThingRepository = otherThingRepository;
        this.someThingRepository = someThingRepository;
        this.someThingElseRepository = someThingElseRepository;
    }

    public void initializeDatabase() {
        Thing thing = new Thing("thing1");
        OtherThing otherThing = new OtherThing("otherthing1");
        ThingToOtherThingRelationship thingToOtherThingRelationship = new ThingToOtherThingRelationship();
        thingToOtherThingRelationship.setThing(thing);
        thingToOtherThingRelationship.setOtherThing(otherThing);
        thingToOtherThingRelationship.setName("myRelationshipName");
        thing.setThingToOtherThingRelationships(Set.of(thingToOtherThingRelationship));

        otherThingRepository.save(otherThing);
        thingRepository.save(thing);

        SomeThing someThing = new SomeThing("something");
        SomeThingElse someThingElse = new SomeThingElse("somethingElse");
        someThing.setSomeThingElses(Set.of(someThingElse));

        someThingElseRepository.save(someThingElse);
        someThingRepository.save(someThing);

        List<SomeThing> somethings = new ArrayList<>();
        someThingRepository.findAll().forEach(somethings::add);
        System.out.println(somethings.size());

    }

    @Test
    @DirtiesContext
    public void testThingToOtherThingRelationship() {
        initializeDatabase();
        String thing1 = "thing1";
        List<Thing> things = new ArrayList<>();
        thingRepository.findAll().forEach(things::add);
        assertThat(things).hasSize(1);
        Thing result = thingRepository.findByName(thing1);
        assertNotNull(result);
        ThingToOtherThingRelationship thingToOtherThingRelationship = result.getThingToOtherThingRelationships().stream().findFirst().get();
        OtherThing otherThing = thingToOtherThingRelationship.getOtherThing();
        assertEquals("otherthing1", otherThing.getOtherThingName());
    }

    @Test
    @DirtiesContext
    public void testSomeThing() {
        initializeDatabase();
        SomeThing someThing = someThingRepository.findByName("something");
        assertThat(someThing.getName()).isEqualTo("something");
        List<SomeThing> somethings = new ArrayList<>();
        someThingRepository.findAll().forEach(somethings::add);
        SomeThingElse someThingElse = somethings.get(0).getSomeThingElses().stream().findFirst().get();
        assertThat(someThingElse.getName()).isEqualTo("somethingElse");
    }

}
