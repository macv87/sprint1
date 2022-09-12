package com.macv.sprint1.repository;

import com.macv.sprint1.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends PersonRepository<User> {

    public UserRepository(){
        add(new User(1,"Usuario 1"));
        add(new User(2,"Usuario 2"));
        add(new User(3,"Usuario 3"));
    }
}
