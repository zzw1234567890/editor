package com.amazing.editor.controller;

import com.alibaba.fastjson.JSON;
import com.amazing.editor.entity.BaseEntity;
import com.amazing.editor.entity.ConfigEntity;
import com.amazing.editor.utils.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.amazing.editor.utils.FilesUtil;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/editor/config")
public class ConfigController {

    @Autowired
    private HttpSession session;

    /**
     * 获取配置
     * @return
     */
    @GetMapping("getConfig")
    public BaseEntity getConfig(){
        BaseEntity result = new BaseEntity();
        String directory = ConfigUtil.getConfigPath(session);
        try {
            String jsonText = FilesUtil.readFileContent(directory);
            result.setContent(JSON.parse(jsonText));
        } catch (IOException e){
            result.setResult(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 修改配置
     * @return
     */
    @PostMapping("setConfig")
    public BaseEntity setConfig(@RequestBody(required = false) ConfigEntity configEntity){
        String directory = ConfigUtil.getConfigPath(session);
        return FilesUtil.writeFileContent(directory, JSON.toJSONString(configEntity));
    }

}
