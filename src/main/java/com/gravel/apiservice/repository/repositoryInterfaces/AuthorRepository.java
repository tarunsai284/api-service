package com.gravel.apiservice.repository.repositoryInterfaces;

import com.gravel.apiservice.models.AuthorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorRepository extends MongoRepository<AuthorDocument,String> {
    AuthorDocument findByAuthorId(String authorId);

    List<AuthorDocument> deleteByAuthorId(String authorId);
}
