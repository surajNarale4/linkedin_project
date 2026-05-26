package com.linkedin.connectionService.repository;

import com.linkedin.connectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person,Long> {


    Optional<Person> findByUserId(Long userId);

    @Query("MATCH (user1:Person)-[:CONNECTED_TO]-(user2:Person)WHERE user1.userId = $userId return user2")
    List<Person> getFirstDegreePersons(Long userId);

}
