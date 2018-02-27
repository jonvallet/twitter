package com.jonvallet.twitter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class TestTwitter {

    @Test
    public void testPostMessage() {

        String user = "Jon";
        String message = "Hello Everyone!";

        ImmutableList<Post> posts = Twitter.post(user, message, ImmutableList.of());
        Post latestPost = posts.get(0);

        assertEquals(user, latestPost.user);
        assertEquals(message, latestPost.message);
    }

    @Test
    public void testPostMessages() {

        String user = "Jon";
        String messageOne = "Hello Everyone!";
        String messageTwo = "I hope you are doing OK.";

        ImmutableList<Post> posts = Twitter.post(user, messageOne, ImmutableList.of());
        posts = Twitter.post(user, messageTwo, posts);

        Post latestPost = posts.get(0);
        assertEquals(user, latestPost.user);
        assertEquals(messageTwo, latestPost.message);

        Post secondPost = posts.get(1);
        assertEquals(user, secondPost.user);
        assertEquals(messageOne, secondPost.message);
    }

    @Test
    public void testReadPost() {
        String userOne = "Jon";
        String userTwo = "Laura";
        String userOneMessage = "Hello, I am Jon!";
        String userTwoMessage = "Hello, I am Laura!";

        ImmutableList<Post> posts = Twitter.post(userOne, userOneMessage, ImmutableList.of());
        posts = Twitter.post(userTwo, userTwoMessage, posts);

        List<Post> userOnePosts = Twitter.read(userOne, posts);
        assertEquals(1, userOnePosts.size());
        Post userOnePost = userOnePosts.get(0);
        assertEquals(userOne, userOnePost.user);
        assertEquals(userOneMessage, userOnePost.message);

        List<Post> userTwoPosts = Twitter.read(userTwo, posts);
        assertEquals(1, userOnePosts.size());
        Post userTwoPost = userTwoPosts.get(0);
        assertEquals(userTwo, userTwoPost.user);
        assertEquals(userTwoMessage, userTwoPost.message);

    }

    @Test
    public void testFollow() {
        String follower = "Jon";
        String user = "Laura";

        ImmutableSet<Follower> followers = Twitter.follow(user, follower, ImmutableSet.of());

        List<String> jonFollows = Twitter.follows(follower, followers);

        assertNotNull(jonFollows);
        assertEquals(1, jonFollows.size());
        assertEquals(user, jonFollows.get(0));
    }

    @Test
    public void testWall() {
        String follower = "Jon";
        String user = "Laura";
        String followerMessage = "Hello, I am Jon!";
        String userMessage = "Hello, I am Laura!";

        ImmutableList<Post> posts = Twitter.post(follower, followerMessage, ImmutableList.of());
        posts = Twitter.post(user, userMessage, posts);
        ImmutableSet<Follower> followers = Twitter.follow(user, follower, ImmutableSet.of());

        List<Post> follewerWall = Twitter.wall(follower, posts, followers);
        assertEquals(2, follewerWall.size());
        Post post1 = follewerWall.get(0);
        assertEquals(user, post1.user);
        assertEquals(userMessage, post1.message);
        Post post2 = follewerWall.get(1);
        assertEquals(follower, post2.user);
        assertEquals(followerMessage, post2.message);
    }

    @Test
    public void testSortedWall() {
        String follower = "Jon";
        String user = "Laura";
        String followerMessage = "Hello, I am Jon!";
        String userMessage = "Hello, I am Laura!";
        String followerMessage2 = "What a nice day.";

        ImmutableList<Post> posts = Twitter.post(follower, followerMessage, ImmutableList.of());
        posts = Twitter.post(user, userMessage, posts);
        posts = Twitter.post(follower, followerMessage2, posts);
        ImmutableSet<Follower> followers = Twitter.follow(user, follower, ImmutableSet.of());

        List<Post> follwerWallPosts = Twitter.wall(follower, posts, followers);
        assertEquals(3, follwerWallPosts.size());

        Post post1 = follwerWallPosts.get(0);
        assertEquals(follower, post1.user);
        assertEquals(followerMessage2, post1.message);

        Post post2 = follwerWallPosts.get(1);
        assertEquals(user, post2.user);
        assertEquals(userMessage, post2.message);

        Post post3 = follwerWallPosts.get(2);
        assertEquals(follower, post3.user);
        assertEquals(followerMessage, post3.message);
    }
}
