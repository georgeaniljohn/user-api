package de.firma.user.service;

import de.firma.user.component.UserImporter;
import de.firma.user.entity.Post;
import de.firma.user.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;

class UserServiceTest {


    @Test
    void testFetchUserAndPostsWithData() throws ExecutionException, InterruptedException {
        UserImporter userImporter = mock(UserImporter.class);
        UserService userService = new UserService(userImporter);
        User user = new User();
        user.setId(1);
        Optional<User> userOptional = Optional.of(user);
        Post post = new Post(1, 1, "title", "body");
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        Optional<List<Post>> postsOptional = Optional.of(posts);
        CompletableFuture<Optional<User>> userFuture = CompletableFuture.completedFuture(userOptional);
        CompletableFuture<Optional<List<Post>>> postsFuture = CompletableFuture.completedFuture(postsOptional);
        when(userImporter.findUser(1)).thenReturn(userFuture);
        when(userImporter.findUserPosts(1)).thenReturn(postsFuture);
        userService.fetchUserAndPosts(1);
        verify(userImporter, timeout(100).times(1)).findUser(1);
        verify(userImporter, timeout(100).times(1)).findUserPosts(1);
    }

    @Test
    void testFetchUserAndPostsWithoutData() throws ExecutionException, InterruptedException {
        UserImporter userImporter = mock(UserImporter.class);
        UserService userService = new UserService(userImporter);
        Optional<User> userOptional = Optional.empty();
        Optional<List<Post>> postsOptional = Optional.empty();
        CompletableFuture<Optional<User>> userFuture = CompletableFuture.completedFuture(userOptional);
        CompletableFuture<Optional<List<Post>>> postsFuture = CompletableFuture.completedFuture(postsOptional);
        when(userImporter.findUser(1)).thenReturn(userFuture);
        when(userImporter.findUserPosts(1)).thenReturn(postsFuture);
        userService.fetchUserAndPosts(1);
        verify(userImporter, timeout(100).times(1)).findUser(1);
        verify(userImporter, timeout(100).times(1)).findUserPosts(1);
    }
}
