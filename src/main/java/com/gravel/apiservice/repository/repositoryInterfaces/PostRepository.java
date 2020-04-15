package com.gravel.apiservice.repository.repositoryInterfaces;

import com.gravel.apiservice.models.AuthorDocument;
import com.gravel.apiservice.models.PostDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<PostDocument, String> {
    PostDocument findByPostId(String postId);

    List<AuthorDocument> deleteByPostId(String postId);

}
