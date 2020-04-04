package com.gravel.apiservice.repository.repositoryInterfaces;

import com.gravel.apiservice.models.AuthorDocument;
import com.gravel.apiservice.repository.repositoryImplementations.AuthorRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AuthorRepositoryImpl{


    MongoTemplate mongoTemplate;

    public List<AuthorDocument> getAllAuthors() {
        return mongoTemplate.findAll(AuthorDocument.class);
    }

    public void getAuthorPosts(String authorId) {
        Query query = new BasicQuery("{authorId: "+authorId+"}");
        mongoTemplate.find(query, AuthorDocument.class);
    }
}
