package com.gravel.apiservice.repository.repositoryInterfaces;

import com.gravel.apiservice.models.PostDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<PostDocument, String> {
    PostDocument findByPostId(String postId);
}
