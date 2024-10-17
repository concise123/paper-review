package com.review.paper_review.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    int save(Post post);
    List<Post> findAll();
    Optional<Post> findById(Long id);
    int update(Post post);
    int delete(Long id);

}
