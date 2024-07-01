package de.firma.user.controller;

import de.firma.user.service.UserService;
import de.firma.user.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void shouldReturnOkay() throws Exception {

        Address address = new Address("Kulas Light","Apt. 556","Gwenborough","2998-3874",new Location("-37.3159","81.1496"));
        Company company = new Company("","","");
        Post post = new Post(1, 1, "title", "body");
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        User user = new User();
        user.setId(1);
        user.setName("test name");
        user.setUsername("sample-user");
        user.setEmail("sample@gmail.com");
        user.setPhone("33421888");
        user.setWebsite("www.someone.com");
        user.setAddress(address);
        user.setCompany(company);
        user.setPosts(posts);
        Optional<User> userOptional = Optional.of(user);
        when(userService.fetchUserAndPosts(1)).thenReturn(userOptional);
        mockMvc.perform(get("/user/1"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldReturnNotOkay() throws Exception {
        Optional<User> userOptional = Optional.empty();
        when(userService.fetchUserAndPosts(1)).thenReturn(userOptional);
        mockMvc.perform(get("/user/1"))
                .andExpect(status().is(500));
    }

}
