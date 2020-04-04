package com.gravel.apiservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
@Data
public class PostDocument {
    @Id
    String id;
    String postId;
    String heading;
    String description;
    String content;
    String authorId;
    String bannerUrl;
}
