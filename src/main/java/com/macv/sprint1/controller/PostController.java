package com.macv.sprint1.controller;

import com.macv.sprint1.dto.MessageResponseDto;
import com.macv.sprint1.dto.PostRequestDto;
import com.macv.sprint1.dto.PostsResponseDto;
import com.macv.sprint1.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //US 0005
    @PostMapping("/products/post")
    public ResponseEntity<MessageResponseDto> postProduct(@RequestBody PostRequestDto post){
        var responseDto = postService.createPost(post);
        return ResponseEntity.ok(responseDto);
    }

    //US 0006
    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<PostsResponseDto> getPosts(@PathVariable int userId, Optional<String> order){
        var responseDto = postService.getPostsFollowedByUserId(userId, order);
        return ResponseEntity.ok(responseDto);
    }

    /*
    *
    * US 0010
    * /products/promo-post
    * post
    *
    * los mismo que el post normal pero se le agregan los campos de has_promo : true y discount: 0.25
    *
    *
    *
    * US 0011
    * /products/promo-post/count?user_id={userId}
    * {
    *   "user_id": 234,
    *   "user_name: "vendedor",
    *   "promo_products_count": 23
    * }
    *
    * US 0012 Obtener un listado de todos los productos en promoción de un determinado vendedor
    * /products/promo-post/list?user_id={userId}
    *
    * {
    *   "user_id": 234,
    *   "user_name": "vendedor",
    *   "posts": [
    *       {
    *           ...
*           }
    *   ]
    * }
    * */
}
