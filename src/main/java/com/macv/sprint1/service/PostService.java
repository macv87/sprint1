package com.macv.sprint1.service;

import com.macv.sprint1.dto.*;
import com.macv.sprint1.exception.SellerNotFoundException;
import com.macv.sprint1.exception.UserNotFoundException;
import com.macv.sprint1.model.Post;
import com.macv.sprint1.repository.FollowsRepository;
import com.macv.sprint1.repository.PostRepository;
import com.macv.sprint1.repository.SellerRepository;
import com.macv.sprint1.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SellerRepository sellerRepository;
    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public PostService(PostRepository postRepository, SellerRepository sellerRepository, FollowsRepository followsRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.sellerRepository = sellerRepository;
        this.followsRepository = followsRepository;
        this.userRepository = userRepository;
        mapper=new ModelMapper();
    }

    public MessageResponseDto createPost(PostRequestDto postDto){
        var post = mapper.map(postDto, Post.class);
        System.out.println(post);
        if(!sellerRepository.existsWithId(post.getUserId()))
            throw new SellerNotFoundException(post.getUserId());
        postRepository.add(post);
        return new MessageResponseDto("Se dió de alta la publicación");
    }

    public PostsResponseDto getPostsFollowedByUserId(int userId, Optional<String> order){
        var user = userRepository.getById(userId);
        if(user.isEmpty()) throw new UserNotFoundException(userId);
        var followedIds = followsRepository.getFollowedIdsForFollower(userId);
        var maxDate = LocalDate.now();
        var minDate = maxDate.minusWeeks(2);
        var posts = new ArrayList<Post>();
        for(Post post : postRepository.getPostsBetween(minDate, maxDate)){
            if(followedIds.contains(post.getUserId()))
                posts.add(post);
        }
        sortPosts(posts, order);
        return PostsResponseDto.builder()
                .userId(userId)
                .posts(posts.stream().map(s-> mapper.map(s, PostResponseDto.class)).collect(Collectors.toList()))
                .build();
    }

    private boolean sortPosts(ArrayList<Post> posts, Optional<String> order){
        if(order.isPresent() && order.get().equals("date_asc"))
            posts.sort(Comparator.comparing(Post::getDate));
        else
            posts.sort(Comparator.comparing(Post::getDate).reversed());
        return true;
    }
}
