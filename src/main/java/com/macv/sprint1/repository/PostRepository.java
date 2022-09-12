package com.macv.sprint1.repository;

import com.macv.sprint1.model.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepository {

    private final List<Post> posts=new ArrayList<>();
    private int currentId=1;

    public List<Post> getAllForUserId(int userId){
        return posts.stream().filter(s-> s.getUserId()==userId).collect(Collectors.toList());
    }


    public boolean add(Post post){
        post.setId(currentId++);
        posts.add(post);
        return true;
    }

    public List<Post> getPostsBetween(LocalDate minDate, LocalDate maxDate) {
        return posts.stream().filter(s ->
                        (s.getDate().isAfter(minDate) || s.getDate().isEqual(minDate)) &&
                                (s.getDate().isBefore(maxDate) || s.getDate().isEqual(maxDate)))
                .collect(Collectors.toList());
    }
}
