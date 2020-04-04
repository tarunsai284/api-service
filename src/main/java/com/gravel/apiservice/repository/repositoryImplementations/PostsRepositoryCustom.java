package com.gravel.apiservice.repository.repositoryImplementations;

import com.gravel.apiservice.models.PostDocument;

import java.util.List;

public interface PostsRepositoryCustom {
    public PostDocument getPostById(String postId);
}
