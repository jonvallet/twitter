package com.jonvallet.twitter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class Twitter {

    public static ImmutableList<Post> post(String user, String message, ImmutableList<Post> posts) {
        return ImmutableList.<Post>builder().add(new Post(user, message)).addAll(posts).build();
    }

    public static ImmutableList<Post> read(String user, ImmutableList<Post> posts) {
        return posts.stream().filter(post -> post.user.equals(user)).collect(toImmutableList());
    }

    public static ImmutableSet<Follower> follow(String user, String follower, ImmutableSet<Follower> followers) {
        return ImmutableSet.<Follower>builder().add(new Follower(user, follower)).addAll(followers).build();
    }

    public static ImmutableList<String> follows(String user, ImmutableSet<Follower> followers) {
        return followers.stream().filter(follower -> follower.follower.equals(user))
                .map(follower -> follower.user).collect(toImmutableList());
    }

    public static ImmutableList<Post> wall(String user, ImmutableList<Post> posts, ImmutableSet<Follower> followers) {
        List<String> following = follows(user, followers);

        final List<Post> wallPosts = new ArrayList<>();

        for(Post post : posts) {
            if (following.contains(post.user) || post.user.equals(user)) {
                wallPosts.add(post);
            }
        }

        return ImmutableList.copyOf(wallPosts);
    }

    private static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toImmutableList() {
        return Collector.of(ImmutableList.Builder<T>::new, ImmutableList.Builder<T>::add, (l, r) -> l.addAll(r.build()),
                ImmutableList.Builder<T>::build);
    }
}
