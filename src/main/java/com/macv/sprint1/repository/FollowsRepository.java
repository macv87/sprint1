package com.macv.sprint1.repository;

import com.macv.sprint1.model.FollowLink;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FollowsRepository {

    private final List<FollowLink> followLinks=new ArrayList<>();

    public boolean addLink(int followerId, int followedId){
        if(existsLink(followerId, followedId)) return false;
        followLinks.add(new FollowLink(followerId, followedId));
        return true;
    }

    public boolean removeLinkWith(int followerId, int followedId){
        var link = getFollowLinkWith(followerId, followedId);
        if(link.isEmpty()) return false;
        followLinks.remove(link.get());
        return true;
    }

    public boolean existsLink(int followerId, int followedId){
        return getFollowLinkWith(followerId, followedId).isPresent();
    }

    public long getFollowersCountFor(int followedId){
        return getFollowerLinksFollowing(followedId).count();
    }

    private Optional<FollowLink> getFollowLinkWith(int followerId, int followedId){
        var links = followLinks.stream().filter(s-> s.getFollowerId()==followerId && s.getFollowedId()==followedId);
        return links.findFirst();
    }

    public List<Integer> getFollowersIdsFollowing(int followedId){
        var links = getFollowerLinksFollowing(followedId);
        return links.map(FollowLink::getFollowerId).collect(Collectors.toList());
    }

    public List<Integer> getFollowedIdsForFollower(int followerId){
        var links = followLinks.stream().filter(s-> s.getFollowerId()==followerId);
        return links.map(FollowLink::getFollowedId).collect(Collectors.toList());
    }

    private Stream<FollowLink> getFollowerLinksFollowing(int followedId){
        return followLinks.stream().filter(s-> s.getFollowedId()==followedId);
    }
}
