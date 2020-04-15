package com.gravel.apiservice.services;

import com.gravel.apiservice.dtos.PostResponseDTO;
import com.gravel.apiservice.models.AuthorDocument;
import com.gravel.apiservice.models.PostDocument;
import com.gravel.apiservice.repository.repositoryInterfaces.AuthorRepository;
import com.gravel.apiservice.repository.repositoryInterfaces.PostCustomRepository;
import com.gravel.apiservice.repository.repositoryInterfaces.PostRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.gravel.grpc.*;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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

        List<PostResponseDTO> postResponseDTO = new ArrayList<>();

        postDocuments.forEach( ele  -> {
            AuthorDocument author = authorRepository.findByAuthorId(ele.getAuthorId());
            PostResponseDTO postResponse = new PostResponseDTO(ele.getPostId(), ele.getHeading(), ele.getDescription(), ele.getContent(), ele.getAuthorId(),
                    ele.getBannerUrl(), author.getFirstName() + " " + author.getLastName(), author.getImageUrl());
            postResponseDTO.add(postResponse);
        });

        Posts.Builder postBuilder = Posts.newBuilder();


        postResponseDTO.forEach(ele -> {
            Post postEle = Post.newBuilder()
                    .setPostId(ele.getPostId())
                    .setHeading(ele.getHeading())
                    .setDescription(ele.getDescription())
                    .setContent(ele.getContent())
                    .setAuthorId(ele.getAuthorId())
                    .setBannerUrl(ele.getBannerUrl())
                    .setAuthorName(ele.getAuthorName())
                    .setAuthorImageUrl(ele.getAuthorImageUrl())
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

    @Override
    public void createPost(PostPayload request, StreamObserver<Get> responseObserver) {
        log.info("createPost is called");
        PostDocument postDocument = new PostDocument();
        Double postId = Math.random() * (10000 - 1000 + 1) + 1000;
        postDocument.setPostId(String.valueOf(postId.longValue()));
        postDocument.setHeading(request.getHeading());
        postDocument.setDescription(request.getDescription());
        postDocument.setContent(request.getContent());
        postDocument.setAuthorId(request.getAuthorId());
        postDocument.setBannerUrl(request.getBannerUrl());

        postRepository.insert(postDocument);
        Get get = Get.newBuilder().build();
        responseObserver.onNext(get);
        responseObserver.onCompleted();
        log.info("createPost processed");
    }

    @Override
    public void createAuthor(Author request, StreamObserver<Get> responseObserver) {
        log.info("createAuthor is called");
        AuthorDocument authorDocument = new AuthorDocument();
        Double authorId = Math.random() * (10000 - 1000 + 1) + 1000;
        authorDocument.setAuthorId(String.valueOf(authorId.longValue()));
        authorDocument.setFirstName(request.getFirstName());
        authorDocument.setLastName(request.getLastName());
        authorDocument.setImageUrl(request.getImageUrl());
        authorDocument.setPosts(new ArrayList<>());
        authorRepository.insert(authorDocument);

        Get get = Get.newBuilder().build();
        responseObserver.onNext(get);
        responseObserver.onCompleted();
        log.info("createAuthor processed");
    }

    @Override
    public void deleteAuthor(AuthorId request, StreamObserver<Get> responseObserver) {
        log.info("deleteAuthor is called");
        authorRepository.deleteByAuthorId(request.getAuthorId());

        Get get = Get.newBuilder().build();
        responseObserver.onNext(get);
        responseObserver.onCompleted();
        log.info("deleteAuthor processed");
    }

    @Override
    public void deletePost(PostId request, StreamObserver<Get> responseObserver) {
        log.info("deletePost is called");
        log.info(postRepository.deleteByPostId(request.getPostId()).toString());

        Get get = Get.newBuilder().build();
        responseObserver.onNext(get);
        responseObserver.onCompleted();
        log.info("deletePost processed");
    }
}
