package com.linkedIn.connectionService.service;

import com.linkedIn.connectionService.dto.PersonDTO;
import com.linkedIn.connectionService.entity.Person;
import com.linkedIn.connectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsService {

    private final PersonRepository personRepository;


    public Set<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("Getting First-degree connection for user {}",userId);
        return personRepository.getFirstDegreePersons(userId);
    }

    public PersonDTO createNewUserInGraphDB(PersonDTO personDTO) {
       Boolean isExist= personRepository.findByUserId(personDTO.getUserId()).isPresent();
       if(isExist){
           throw new NoSuchElementException("User is Already present with given Id");
       }
       Person person = Person.builder()
               .userId(personDTO.getUserId())
               .name(personDTO.getName())
               .build();

        personRepository.save(person);
        return personDTO;


    }
}
