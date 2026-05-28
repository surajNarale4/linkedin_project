package com.linkedIn.postsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    private Long userId;//postgres db

    private String name;
}
