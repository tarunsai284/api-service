package com.gravel.apiservice.repository.repositoryInterfaces;

import com.gravel.apiservice.models.AuthorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<AuthorDocument,String> {
    AuthorDocument findByAuthorId(String authorId);
}
