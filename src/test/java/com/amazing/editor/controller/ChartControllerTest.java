package com.amazing.editor.controller;

import com.alibaba.fastjson.JSON;
import com.amazing.editor.entity.MessageEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChartControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MessageEntity messageEntity;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }

    @Test
    public void sendMessage() throws Exception {
        messageEntity.setContent("success");
        String data = JSON.toJSONString(messageEntity);
        // post
        mvc.perform(MockMvcRequestBuilders.post("/editor/chart/sendMessage")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(data).session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getHistoryMessage() throws Exception{
        // get
        mvc.perform(MockMvcRequestBuilders.get("/editor/chart/getHistoryMessage?offset=0&limit=10").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}