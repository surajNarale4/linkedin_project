package com.linkedin.connectionService.controller;

import com.linkedin.connectionService.entity.Person;
import com.linkedin.connectionService.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("core")
public class ConnectionController {
    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstDegreeConnections(@PathVariable Long userId){
        List<Person> personList=connectionsService.getFirstDegreeConnectionsOfUser(userId);
        return new ResponseEntity<>(personList, HttpStatusCode.valueOf(200));
    }
}
