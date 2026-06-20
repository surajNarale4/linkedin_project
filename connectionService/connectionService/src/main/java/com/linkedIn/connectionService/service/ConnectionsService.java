package com.linkedIn.connectionService.service;

import com.linkedIn.connectionService.auth.AuthContextHolder;
import com.linkedIn.connectionService.dto.PersonDTO;
import com.linkedIn.connectionService.entity.Person;
import com.linkedIn.connectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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


    public void sendConnectionRequest(Long receiver) {
        Long sender=AuthContextHolder.getCurrentUserId();
        if(isRequestAlreadyPresent(sender,receiver)){
            throw new RuntimeException("connection request already present between users");
        }
        personRepository.addConnectionRequest(sender,receiver);
        log.info("successfully request sent to {} by {}",sender,receiver);
    }

    public boolean isRequestAlreadyPresent(Long sender, Long receiver){
     if(isConnectAreadyPresent(sender,receiver)){
         throw new RuntimeException("connection already present between user");
     }
     return personRepository.connectionRequestExists(sender,receiver);
    }

    public boolean isConnectAreadyPresent(Long sender , Long receiver){
        return personRepository.alreadyConnected(sender,receiver);

    }

    public void acceptTheRequest(Long requester) {

        Long accepter = AuthContextHolder.getCurrentUserId();
        if(isConnectAreadyPresent(requester,accepter)){
            throw new RuntimeException("There is already connection between two users");
        }
        //check is there request or not from requester first
        if(!isRequestAlreadyPresent(requester,accepter)){
            throw new RuntimeException("There is no request from "+requester);
        }
        personRepository.acceptConnectionRequest(requester,accepter);
        log.info("successfuly connection request created in between {} {}",requester,accepter);
    }
}
