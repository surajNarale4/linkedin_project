package com.linkedIn.connectionService.repository;

import com.linkedIn.connectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;
import java.util.Set;

public interface PersonRepository extends Neo4jRepository<Person,Long> {


    Optional<Person> findByUserId(Long userId);

    @Query("MATCH (user1:Person)-[:CONNECTED_TO]-(user2:Person)WHERE user1.userId = $userId return user2")
    Set<Person> getFirstDegreePersons(Long userId);

}
