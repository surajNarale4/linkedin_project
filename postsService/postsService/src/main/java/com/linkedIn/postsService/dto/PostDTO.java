package com.linkedIn.postsService.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostDTO {

    private Long id;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
}
