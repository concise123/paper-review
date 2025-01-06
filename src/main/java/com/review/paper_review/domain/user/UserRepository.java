package com.review.paper_review.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    int save(User user);
    List<User> findAll();
    Optional<User> findById(UserPK id);
    int updateRole(User user);
    int delete(UserPK id);

}
