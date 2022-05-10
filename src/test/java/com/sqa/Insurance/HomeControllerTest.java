package com.sqa.Insurance;

import com.sqa.Insurance.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

   @Before
   public void setup(){
//       DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
//       this.mockMvc =builder.build();
   }

    @Test
    public void registerPass() throws Exception{

        User user = new User();

        user.setUsername("LUONG8822375427");
        user.setIs_active(false);
        user.setName("Bong");
        user.setCccd("164678962");
        user.setPhone("0337714368");

//        HomeController homeController = new HomeController();
//
//        User userFound = homeSevice.findByUsername(user.getUsername());
//
//        Assertions.assertEquals(true,homeController.check(user, userFound));

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .sessionAttr("user", user)
        ).andExpect(status().isOk());


    }

}