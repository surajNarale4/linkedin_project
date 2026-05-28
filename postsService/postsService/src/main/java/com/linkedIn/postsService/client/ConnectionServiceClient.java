package com.linkedIn.postsService.client;

import com.linkedIn.postsService.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name="CONNECTIONSERVICE" , path="connections")
public interface ConnectionServiceClient {

    @GetMapping("core/{userId}/first-degree")
    List<PersonDTO> getFirstDegreeConnections(@PathVariable Long userId);
}
