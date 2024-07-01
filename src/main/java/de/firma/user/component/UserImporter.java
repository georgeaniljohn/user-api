package de.firma.user.component;

import de.firma.user.entity.User;
import de.firma.user.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Service
public class UserImporter {

    private static final Logger LOG = LoggerFactory.getLogger(UserImporter.class);

    private final WebClient webClient;

    public UserImporter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Async
    public CompletableFuture<Optional<User>> findUser(Integer userId) {
        long start = System.currentTimeMillis();
        LOG.debug("Lookup of user started for [{}]", userId);
        User user = null;
        try {
            user = webClient.get()
                    .uri(String.format("/users/%s", userId))
                    .retrieve().bodyToMono(User.class).block(Duration.ofSeconds(30));
        } catch (WebClientException e) {
            LOG.error("Error when calling findUser for [{}]:", userId, e);
        }

        LOG.debug("Lookup of user ended for [{}] in [{}]ms", userId, System.currentTimeMillis() - start);
        return CompletableFuture.completedFuture(user != null ? Optional.of(user) : Optional.empty());
    }

    @Async
    public CompletableFuture<Optional<List<Post>>> findUserPosts(Integer userId) {
        long start = System.currentTimeMillis();
        LOG.info("Lookup of posts started for user [{}]", userId);
        Post[] postArray = new Post[]{};
        try {
            postArray = webClient.get()
                    .uri(String.format("/posts?userId=%s", userId))
                    .retrieve().bodyToMono(Post[].class).block(Duration.ofSeconds(30));
        } catch (WebClientException e) {
            LOG.error("Error when calling findUserPosts for [{}]:", userId, e);
        }

        LOG.debug("Lookup of posts ended [{}] in [{}]ms", userId, System.currentTimeMillis() - start);
        return CompletableFuture.completedFuture(postArray != null && postArray.length > 0 ?
                Optional.of(Arrays.asList(postArray)) :
                Optional.empty());
    }

}
