package com.gravel.apiservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "authors")
@Data
public class AuthorDocument {
    @Id
    String id;
    String authorId;
    String firstName;
    String lastName;
    String imageUrl;
    List<String> posts;
}
