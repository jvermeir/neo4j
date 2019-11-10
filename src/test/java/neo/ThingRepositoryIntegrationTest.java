package neo;

import neo.config.Neo4jTestConfiguration;
import neo.domain.MyRelationship;
import neo.domain.OtherThing;
import neo.domain.Thing;
import neo.repository.OtherThingRepository;
import neo.repository.ThingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class ThingRepositoryIntegrationTest {

    @Autowired
    private ThingRepository thingRepository;

    @Autowired
    private OtherThingRepository otherThingRepository;

    public ThingRepositoryIntegrationTest() {
    }

    @Before
    public void initializeDatabase() {
        System.out.println("seeding embedded database");
        Thing thing = new Thing("thing1");
        OtherThing otherThing = new OtherThing("otherthing1");
        MyRelationship myRelationship = new MyRelationship();
        myRelationship.setThing(thing);
        myRelationship.setOtherThing(otherThing);

        otherThingRepository.save(otherThing);
        thingRepository.save(thing);
    }

    @Test
    @DirtiesContext
    public void testFindByName() {
        System.out.println("findByNamee");
        String thing1 = "thing1";
        Iterator<Thing> iterator = thingRepository.findAll().iterator();
        while (iterator.hasNext()) {
            Thing next = iterator.next();
            System.out.println(next);
        }

        Thing result = thingRepository.findByName(thing1);
        assertNotNull(result);
        MyRelationship myRelationship = result.getMyRelationships().stream().findFirst().get();
        OtherThing otherThing = myRelationship.getOtherThing();
        assertEquals("otherthing1", otherThing.getOtherThingName());
    }

}
