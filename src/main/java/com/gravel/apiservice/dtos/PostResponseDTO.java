package com.gravel.apiservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class PostResponseDTO {
    String postId;
    String heading;
    String description;
    String content;
    String authorId;
    String bannerUrl;
    String authorName;
    String authorImageUrl;
}
