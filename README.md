# Neo4j experiments

Try out Neo4j and Spring integration to learn about mappings and how parts of the object graph are persisted.

## Build 

Run integration tests 

    mvn clean verify
    
## Connection to Neo4j test container 

Start the integration test, set a breakpoint. When the test stops you can connect to the Neo4j database
that was started as part of the test:

    docker ps # this will show the port mappings
    # find the port for the web interface, that would be the port mapped to 7474 in the container
    # enter `localhost:<webport>/browser` in your browser
    # enter `bolt://localhost:32853` in the url field for the connection to Neo4j 
    # enter `password` in the password field 
    
## Acknowledgements

Thanks to (Michael Simons)[https://medium.com/@michael.simons] for his 
(blog)[https://medium.com/neo4j/testing-your-neo4j-based-java-application-34bef487cc3c]    
that helped me make sense of testing my code. 
