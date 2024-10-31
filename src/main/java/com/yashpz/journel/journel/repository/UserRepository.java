package com.yashpz.journel.journel.repository;

import com.yashpz.journel.journel.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);

    void deleteByUsername(String username);
}
