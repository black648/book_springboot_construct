package com.hkl.sbConstruct.web;

import com.hkl.sbConstruct.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) //테스트 수행 시 JUnit에 내장된 실행자 외 다른 실행자(SpringRunner)를 실행 시킴. (SB테스트와 JUnit 사이 연결자 역할)
@WebMvcTest(controllers = TestController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
}) //@Controller 테스트에 사용되는
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void test1() throws Exception {
        String hello = "hello";

        mvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
}