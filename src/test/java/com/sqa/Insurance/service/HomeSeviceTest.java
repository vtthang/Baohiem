package com.sqa.Insurance.service;

import com.sqa.Insurance.model.User;
import com.sqa.Insurance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeSeviceTest {

    @Autowired
    HomeSevice homeSevice;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsernameSuccess() {
        String expectedUserName = "CBCNV9475463727";
        User user = homeSevice.findByUsername(expectedUserName);
        assertEquals(expectedUserName, user.getUsername());
    }

    @Test
    void findByUsernameFailure() {
        assertNull(homeSevice.findByUsername("Hang PTIT"));
    }

    @Test
    void findByIdSuccess() {
        int expectedId = 13;
        User user = homeSevice.findUserById(13);
        assertEquals(expectedId, user.getId());
    }

    @Test
    void findByIdFailure() {
        assertNull(homeSevice.findUserById(-1));
    }


    @Test
    void testGetAllUserExcepAdmin() {
        int expectedSize = 26;
        assertEquals(expectedSize, homeSevice.getAllUserExceptAdmin().size());
    }

    @Test
    void findByUsernameAndCCCDSuccess() {
        String username = "CBCNV6615138220";
        String cccd = "32323";
        User user = homeSevice.getUserByUsernameAndCCCD(username, cccd);
        long expectedId = 26L;
        assertEquals(expectedId, user.getId());
    }

    @Test
    void findByUsernameAndCCCDFailureByUserName() {
        String username = "Hang PTIT";
        String cccd = "32323";
        assertNull(homeSevice.getUserByUsernameAndCCCD(username, cccd));
    }

    @Test
    void findByUsernameAndCCCDFailureByCCCD() {
        String username = "CBCNV6615138220";
        String cccd = "-1";
        assertNull(homeSevice.getUserByUsernameAndCCCD(username, cccd));
    }





}