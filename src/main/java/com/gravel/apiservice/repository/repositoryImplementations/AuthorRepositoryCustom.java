package com.gravel.apiservice.repository.repositoryImplementations;

import com.gravel.apiservice.models.AuthorDocument;

import java.util.List;

public interface AuthorRepositoryCustom {
    public List<AuthorDocument> getAllAuthors();
    public void getAuthorPosts(String authorId);
}
