package com.sqa.Insurance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.Insurance.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class HomeControllerTest {

    @Autowired
    private WebApplicationContext context;


    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void index() {
    }

    @Before
    private void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void login() throws Exception {
        User user = new User();
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("login"));

    }

    @Test
    void register() throws Exception {
        User user = new User();
        mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(model().attribute("user", user))
                .andExpect(view().name("register"));

    }

    @Test
    void registerPost() throws Exception {
        User user = new User();
//        user.setUsername("haha");
//
//        String res = objectMapper.writeValueAsString(user);
//        Mockito.when()
//
//        mockMvc.perform(post("/register").sessionAttr("user", user).content(res).content(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(view().name("register"));
    }

    @Test
    void chooseimgPost() {
    }

    @Test
    void profile() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(view().name("login"));
    }

    @Test
    void payment() {
    }

    @Test
    void paymentPost() {
    }

    @Test
    void saveFile() {
    }
}