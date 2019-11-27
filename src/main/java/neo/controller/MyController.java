package neo.controller;

import neo.domain.ThingToOtherThingRelationship;
import neo.domain.OtherThing;
import neo.domain.Thing;
import neo.repository.OtherThingRepository;
import neo.repository.ThingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class MyController {
    @Autowired
    ThingRepository thingRepository;
    @Autowired
    OtherThingRepository otherThingRepository;

    @GetMapping("/go")
    public Thing get() {
        Thing thing = new Thing("thing1");
        OtherThing otherThing = new OtherThing("otherthing1");
        ThingToOtherThingRelationship thingToOtherThingRelationship = new ThingToOtherThingRelationship();
        thingToOtherThingRelationship.setThing(thing);
        thingToOtherThingRelationship.setOtherThing(otherThing);
        thingToOtherThingRelationship.setName("myRelationshipName");
        thing.setThingToOtherThingRelationships(Set.of(thingToOtherThingRelationship));

        otherThingRepository.save(otherThing);
        thingRepository.save(thing);
        return thing;
    }

    @GetMapping("/name/{name}")
    public Thing getByName(@PathVariable(value="name") String name) {
        return thingRepository.findByName(name);
    }

    @DeleteMapping("/delete")
    public void delete() { thingRepository.deleteAll(); }
}
