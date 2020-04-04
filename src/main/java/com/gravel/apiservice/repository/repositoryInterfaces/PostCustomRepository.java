package com.gravel.apiservice.repository.repositoryInterfaces;

import com.gravel.apiservice.models.PostDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class PostCustomRepository {
    @Resource(name="mongoTemplate")
    MongoTemplate mongoTemplate;

    public PostDocument getPostById(String postId) {
        Query query = new Query(where("postId").is(postId));
        return mongoTemplate.findOne(query, PostDocument.class);
    }

    public List<PostDocument> getPosts(List<String> postIds) {
        Query query = new Query(where("postId").in(postIds));
        return mongoTemplate.find(query, PostDocument.class);
    }

}
