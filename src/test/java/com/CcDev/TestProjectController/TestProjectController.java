package com.CcDev.TestProjectController;

import com.alibaba.fastjson.JSONPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Cui
 * @date 2022/3/29 - 15:24
 * @email yuxiangcui6@gmail.com
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestProjectController {
    @Resource
    private MockMvc mockMvc;
    String sessionId;

    @Before
    public void testLogin() throws Exception {
        String resultJson = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .param("username", "024@qq.com")
                        .param("password", "e10adc3949ba59abbe56e057f20f883e")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        sessionId = (String) JSONPath.read(resultJson, "$.message");
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/find")
                        .header("Authorization", sessionId)
                        .param("username", "022@qq.com")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathEquals("$.message", "账号已存在"))
                .andExpect(jsonPathEquals("$.status", "0"))
                .andReturn().getResponse().getContentAsString();
    }

    public ResultMatcher jsonPathEquals(String matcher, String expected) {
        return MockMvcResultMatchers.jsonPath(matcher, Matchers.is(expected));
    }

}
