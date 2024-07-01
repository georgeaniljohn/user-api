package de.firma.user.service;

import de.firma.user.component.UserImporter;
import de.firma.user.entity.Post;
import de.firma.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserImporter userImporter;

    public UserService(UserImporter userImporter) {
        this.userImporter = userImporter;
    }

    @Cacheable("user")
    public Optional<User> fetchUserAndPosts(Integer userId) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        LOG.debug("Fetch Data started for user[{}]", userId);
        CompletableFuture<Optional<User>> userFuture = userImporter.findUser(userId);
        CompletableFuture<Optional<List<Post>>> postsFuture = userImporter.findUserPosts(userId);

        CompletableFuture.allOf(userFuture, postsFuture);

        Optional<User> userOptional = userFuture.get();
        Optional<List<Post>> postsOptional = postsFuture.get();
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (postsOptional.isPresent()) {
                user.setPosts(postsOptional.get());
            }
            LOG.debug("Fetch Data ended with success for user[{}] in [{}]ms", userId,
                    System.currentTimeMillis() - start);
            return Optional.of(user);
        } else {
            LOG.debug("Fetch Data ended with failure for user[{}] in [{}]ms", userId,
                    System.currentTimeMillis() - start);
            return Optional.empty();
        }
    }
}
