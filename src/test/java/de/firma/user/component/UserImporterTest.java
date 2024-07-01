package de.firma.user.component;

import de.firma.user.entity.Post;
import de.firma.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class UserImporterTest {

    @Test
    void testFindUser() {
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        Mono mono = mock(Mono.class);
        User user = new User();
        user.setId(1);
        String url = String.format("/users/%s", 1);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(url)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(User.class)).thenReturn(mono);
        when(mono.block(Duration.ofSeconds(anyInt()))).thenReturn(user);
        UserImporter userImporter = new UserImporter(webClient);
        assertDoesNotThrow(() -> userImporter.findUser(1));
    }


    @Test
    void testFindUserPosts() {

        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        Mono mono = mock(Mono.class);
        Post post = new Post(1, 1, "title", "body");
        Post[] posts = new Post[]{post};
        String url = String.format("/posts?userId=%s", 1);
        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(url)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Post[].class)).thenReturn(mono);
        when(mono.block(Duration.ofSeconds(anyInt()))).thenReturn(posts);
        UserImporter userImporter = new UserImporter(webClient);
        assertDoesNotThrow(() -> userImporter.findUserPosts(1));
    }
}
