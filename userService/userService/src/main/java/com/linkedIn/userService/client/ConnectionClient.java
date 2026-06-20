package com.linkedIn.userService.client;

import com.linkedIn.userService.dto.PersonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name="CONNECTIONSERVICE",path="connections")
public interface ConnectionClient {
    @PostMapping("core/create-user")
    PersonDTO createUserInGrapDB(@RequestBody PersonDTO personDTO);
}
