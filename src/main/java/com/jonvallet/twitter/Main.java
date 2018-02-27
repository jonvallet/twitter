package com.jonvallet.twitter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ImmutableList<Post> posts = ImmutableList.of();
        ImmutableSet<Follower> followers = ImmutableSet.of();

        final String HELP = "Twitter Console\n" +
                            "posting: <user name> -> <message>\n" +
                            "reading: <user name>\n" +
                            "following: <user name> follows <other user>\n" +
                            "wall: <user name> wall\n" +
                            "Exit: exit";

        System.out.println(HELP);

        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("exit")) {
            String [] lineArgs = line.split(" ");
            String user = lineArgs[0];

            if (lineArgs.length == 1) {
                Twitter.read(user, posts).forEach(System.out::println);
            } else {
                String command = lineArgs[1];

                switch (command) {
                    case "->" :
                        String message = String.join(" ", Arrays.copyOfRange(lineArgs, 2, lineArgs.length));
                        posts = Twitter.post(user, message, posts);
                        break;
                    case "follows":
                        String follow = lineArgs[2];
                        followers = Twitter.follow(follow, user, followers);
                        break;
                    case "wall":
                        Twitter.wall(user, posts, followers).forEach(System.out::println);
                        break;
                }
            }

            line = scanner.nextLine();
        }
    }
}
