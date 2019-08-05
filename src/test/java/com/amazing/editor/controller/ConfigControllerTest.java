package com.amazing.editor.controller;

import com.alibaba.fastjson.JSON;
import com.amazing.editor.entity.ConfigEntity;
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
public class ConfigControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ConfigEntity configEntity;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }

    @Test
    public void getConfig() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/editor/config/getConfig").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void setConfig() throws Exception {
        configEntity.setAutoSave(true);
        configEntity.setAutoSaveTime(2);
        String data = JSON.toJSONString(configEntity);
        mvc.perform(MockMvcRequestBuilders.post("/editor/config/setConfig")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(data).session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }
}