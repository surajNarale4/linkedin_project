package com.linkedin.connectionService.service;

import com.linkedin.connectionService.entity.Person;
import com.linkedin.connectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("Getting First-degree connection for user {}",userId);
        return personRepository.getFirstDegreePersons(userId);
    }

}
