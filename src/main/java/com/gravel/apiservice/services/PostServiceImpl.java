package com.gravel.apiservice.services;

import com.gravel.apiservice.models.AuthorDocument;
import com.gravel.apiservice.models.PostDocument;
import com.gravel.apiservice.repository.repositoryInterfaces.AuthorRepository;
import com.gravel.apiservice.repository.repositoryInterfaces.PostRepository;
import com.gravel.apiservice.repository.repositoryInterfaces.PostCustomRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.gravel.grpc.*;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.List;

@Slf4j
@GRpcService
public class PostServiceImpl extends PostServiceGrpc.PostServiceImplBase {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostCustomRepository postCustomRepository;

    @Override
    public void getPost(PostId request, StreamObserver<Post> responseObserver) {
        log.info("getPost called with request:{}", request.toString());

        PostDocument postDoc = postCustomRepository.getPostById(request.getPostId());
        Post post = Post.newBuilder()
                .setPostId(postDoc.getPostId())
                .setHeading(postDoc.getHeading())
                .setDescription(postDoc.getDescription())
                .setContent(postDoc.getContent())
                .setAuthorId(postDoc.getAuthorId())
                .setBannerUrl(postDoc.getBannerUrl())
                .build();
        responseObserver.onNext(post);
        responseObserver.onCompleted();
        log.info("getPost processed with Response: {}", post.toString());

    }

    @Override
    public void getAllPosts(Get request, StreamObserver<Posts> responseObserver) {
        log.info("getAllPosts is called");
        List<PostDocument> postDocuments = postRepository.findAll();
        log.debug("posts: {}", postDocuments.toArray());
        Posts.Builder postBuilder = Posts.newBuilder();

        postDocuments.forEach(ele -> {
            Post postEle = Post.newBuilder()
                    .setPostId(ele.getPostId())
                    .setHeading(ele.getHeading())
                    .setDescription(ele.getDescription())
                    .setContent(ele.getContent())
                    .setAuthorId(ele.getAuthorId())
                    .setBannerUrl(ele.getBannerUrl())
                    .build();
            postBuilder.addPosts(postEle);
        });
        Posts posts = postBuilder.build();
        responseObserver.onNext(posts);
        responseObserver.onCompleted();
        log.info("getAllPosts processed with Response: {}", posts.toString());
    }

    @Override
    public void getAuthors(Get request, StreamObserver<Authors> responseObserver) {
        log.info("getAuthors is called");
        List<AuthorDocument> authorDocuments = authorRepository.findAll();
        log.debug("authors: {}", authorDocuments.toArray());
        Authors.Builder authorsBuilder = Authors.newBuilder();

        authorDocuments.forEach(ele -> {
            Author author = Author.newBuilder()
                    .setAuthorId(ele.getAuthorId())
                    .setFirstName(ele.getFirstName())
                    .setLastName(ele.getLastName())
                    .setImageUrl(ele.getImageUrl())
                    .setAuthorId(ele.getAuthorId())
                    .build();
            authorsBuilder.addAuthors(author);
        });
        Authors authors = authorsBuilder.build();
        responseObserver.onNext(authors);
        responseObserver.onCompleted();
        log.info("getAllPosts processed with Response: {}", authors.toString());

    }

    @Override
    public void getAuthorPosts(AuthorId request, StreamObserver<Posts> responseObserver) {
        log.info("getAuthorPosts is called");
        log.info("getByFirstName is called");
        AuthorDocument authorDoc = authorRepository.findByAuthorId(request.getAuthorId());
        List<String> postIds = authorDoc.getPosts();
        List<PostDocument> postsList = postCustomRepository.getPosts(postIds);
        Posts.Builder postBuilder = Posts.newBuilder();

        postsList.forEach(ele -> {
            Post postEle = Post.newBuilder()
                    .setPostId(ele.getPostId())
                    .setHeading(ele.getHeading())
                    .setDescription(ele.getDescription())
                    .setContent(ele.getContent())
                    .setAuthorId(ele.getAuthorId())
                    .setBannerUrl(ele.getBannerUrl())
                    .build();
            postBuilder.addPosts(postEle);
        });
        Posts posts = postBuilder.build();
        responseObserver.onNext(posts);
        responseObserver.onCompleted();
        log.info("getAllPosts processed with Response: {}", posts.toString());
    }

}
