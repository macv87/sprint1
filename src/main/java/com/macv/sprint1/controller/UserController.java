package com.macv.sprint1.controller;

import com.macv.sprint1.dto.*;
import com.macv.sprint1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hi")
    public String hi() {
        return "Hola mundo";
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    //US 0001
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<MessageResponseDto> follow(@PathVariable int userId, @PathVariable int userIdToFollow) {
        var responseDto = userService.follow(userId, userIdToFollow);
        return ResponseEntity.ok(responseDto);
    }

    //US 0007
    @GetMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<MessageResponseDto> unfollow(@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        var responseDto = userService.unfollow(userId, userIdToUnfollow);
        return ResponseEntity.ok(responseDto);
    }

    //US 0002
    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<FollowersCountResponseDto> getFollowersCount(@PathVariable int userId) {
        var responseDto = userService.getFollowersCountResponseDtoFor(userId);
        return ResponseEntity.ok(responseDto);
    }

    //US 0003 ¿Quién me sigue?
    @GetMapping("/users/{userId}/followers/list")
    public ResponseEntity<FollowersListResponseDto> getFollowersList(@PathVariable int userId, @RequestParam Optional<String> order){
        var responseDto = userService.getFollowersListFor(userId, order);
        return ResponseEntity.ok(responseDto);
    }

    //US 0004 ¿A quién sigo?
    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<FollowingListResponseDto> getFollowingList(@PathVariable int userId, @RequestParam Optional<String> order){
        var responseDto = userService.getFollowingListFor(userId, order);
        return ResponseEntity.ok(responseDto);
    }
}
