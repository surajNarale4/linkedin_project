package com.linkedIn.connectionService.controller;

import com.linkedIn.connectionService.dto.PersonDTO;
import com.linkedIn.connectionService.entity.Person;
import com.linkedIn.connectionService.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("core")
public class ConnectionController {
    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<Set<Person>> getFirstDegreeConnections(@PathVariable Long userId) {
        Set<Person> personList = connectionsService.getFirstDegreeConnectionsOfUser(userId);
        return new ResponseEntity<>(personList, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/create-user")
    public ResponseEntity<PersonDTO> createUserInGrapDB(@RequestBody PersonDTO personDTO) {
        return ResponseEntity.ok(connectionsService.createNewUserInGraphDB(personDTO));
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Void> sendConnectionRequest(@PathVariable Long userId) {
        connectionsService.sendConnectionRequest(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long userId){
        connectionsService.acceptTheRequest(userId);
        return ResponseEntity.noContent().build();
    }


}
