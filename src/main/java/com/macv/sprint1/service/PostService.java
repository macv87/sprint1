package com.macv.sprint1.service;

import com.macv.sprint1.dto.*;
import com.macv.sprint1.exception.SellerNotFoundException;
import com.macv.sprint1.exception.UserNotFoundException;
import com.macv.sprint1.model.Post;
import com.macv.sprint1.model.Product;
import com.macv.sprint1.repository.FollowsRepository;
import com.macv.sprint1.repository.PostRepository;
import com.macv.sprint1.repository.SellerRepository;
import com.macv.sprint1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final SellerRepository sellerRepository;
    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, SellerRepository sellerRepository, FollowsRepository followsRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.sellerRepository = sellerRepository;
        this.followsRepository = followsRepository;
        this.userRepository = userRepository;
    }

    public MessageResponseDto createPost(PostRequestDto postDto){
        var post = getPostFrom(postDto);
        if(!sellerRepository.existsWithId( post.getUserId()))
            throw new SellerNotFoundException(post.getUserId());
        postRepository.add(post);
        return new MessageResponseDto("Se dió de alta la publicación");
    }

    private Post getPostFrom(PostRequestDto data){
        var postBuilder = Post.builder()
                .userId(data.getUser_id())
                .date(getDate(data.getDate()))
                .product(createProduct(data.getProduct()))
                .category(data.getCategory())
                .price(data.getPrice());
        return postBuilder.build();
    }

    private LocalDate getDate(String date) {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Product createProduct(ProductRequestDto data){
        var productBuilder = Product.builder()
                .productId(data.getProduct_id())
                .productName(data.getProduct_name())
                .type(data.getType())
                .brand(data.getBrand())
                .color(data.getColor())
                .notes(data.getNotes());
        return productBuilder.build();
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
                .user_id(userId)
                .posts(posts.stream().map(this::convertToPostResponse).collect(Collectors.toList()))
                .build();
    }

    private boolean sortPosts(ArrayList<Post> posts, Optional<String> order){
        if(order.isPresent() && order.get().equals("date_asc"))
            posts.sort(Comparator.comparing(Post::getDate));
        else
            posts.sort(Comparator.comparing(Post::getDate).reversed());
        return true;
    }

    private PostResponseDto convertToPostResponse(Post post){
        return PostResponseDto.builder()
                .user_id(post.getUserId())
                .post_id(post.getId())
                .date(toDateString(post.getDate()))
                .product(convertToProductRequestDto(post.getProduct()))
                .category(post.getCategory())
                .price(post.getPrice())
                .build();
    }

    private String toDateString(LocalDate date){
        if(date==null) return "";
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    private ProductRequestDto convertToProductRequestDto(Product product){
        return ProductRequestDto.builder()
                .product_id(product.getProductId())
                .product_name(product.getProductName())
                .type(product.getType())
                .brand(product.getBrand())
                .color(product.getColor())
                .notes(product.getNotes())
                .build();
    }
}
