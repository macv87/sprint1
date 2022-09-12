package com.macv.sprint1.service;

import com.macv.sprint1.dto.*;
import com.macv.sprint1.exception.SellerNotFoundException;
import com.macv.sprint1.exception.UserNotFoundException;
import com.macv.sprint1.model.Person;
import com.macv.sprint1.repository.FollowsRepository;
import com.macv.sprint1.repository.PersonRepository;
import com.macv.sprint1.repository.SellerRepository;
import com.macv.sprint1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final FollowsRepository followsRepository;

    @Autowired
    public UserService(UserRepository userRepository, SellerRepository sellerRepository, FollowsRepository followsRepository) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.followsRepository = followsRepository;
    }

    public MessageResponseDto follow(int followerId, int followedId) {
        validateIds(followerId, followedId);
        if (!followsRepository.addLink(followerId, followedId))
            return new MessageResponseDto("Este follow ya existía.");
        return new MessageResponseDto("Ahora el usuario con id " + followerId + " sigue al vendedor con id " + followedId);
    }

    public MessageResponseDto unfollow(int followerId, int followedId) {
        validateIds(followerId, followedId);
        if (!followsRepository.removeLinkWith(followerId, followedId))
            return new MessageResponseDto("Este follow no existe.");
        return new MessageResponseDto("El usuario con id " + followerId + " dejó de seguir al vendedor con id " + followedId);
    }

    private boolean validateIds(int followerId, int followedId) {
        if (!userRepository.existsWithId(followerId)) throw new UserNotFoundException(followerId);
        if (!sellerRepository.existsWithId(followedId)) throw new SellerNotFoundException(followedId);
        return true;
    }

    public FollowersCountResponseDto getFollowersCountResponseDtoFor(int followedId) {
        var seller = sellerRepository.getById(followedId);
        if (seller.isEmpty()) throw new SellerNotFoundException(followedId);
        var followersCount = followsRepository.getFollowersCountFor(followedId);
        return new FollowersCountResponseDto(followedId, seller.get().getName(), followersCount);
    }

    public FollowersListResponseDto getFollowersListFor(int userId, Optional<String> order) {
        var seller = sellerRepository.getById(userId);
        if (seller.isEmpty()) throw new SellerNotFoundException(userId);
        var followerIds = followsRepository.getFollowersIdsFollowing(userId);
        var followers = getUserDtosFrom(followerIds, userRepository, order);
        return new FollowersListResponseDto(userId, seller.get().getName(), followers);
    }

    public FollowingListResponseDto getFollowingListFor(int userId, Optional<String> order) {
        var user = userRepository.getById(userId);
        if (user.isEmpty()) throw new UserNotFoundException(userId);
        var followedIds = followsRepository.getFollowedIdsForFollower(userId);
        var followings = getUserDtosFrom(followedIds, sellerRepository, order);
        return new FollowingListResponseDto(userId, user.get().getName(), followings);
    }

    private <T extends Person> List<UserResponseDto> getUserDtosFrom(List<Integer> ids, PersonRepository<T> personRepository, Optional<String> order) {
        var userDtos = new ArrayList<UserResponseDto>();
        for (Integer followerId : ids) {
            var user = personRepository.getById(followerId);
            user.ifPresent(value -> userDtos.add(new UserResponseDto(value)));
        }
        order.ifPresent(s -> sort(userDtos, s));
        return userDtos;
    }

    private boolean sort(List<UserResponseDto> userDtos, String order){
        switch (order){
            case "name_asc":
                userDtos.sort(Comparator.comparing(UserResponseDto::getUserName));
                return true;
            case "name_desc":
                userDtos.sort(Comparator.comparing(UserResponseDto::getUserName).reversed());
                return true;
            default:
                return false;
        }
    }
}
