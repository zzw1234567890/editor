package com.amazing.editor.controller;

import com.amazing.editor.entity.Log.PathLogEntity;
import com.amazing.editor.utils.DaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/editor/log/")
public class LogController {

    @Autowired
    private HttpSession session;

    /**
     * 获取历史日志
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("getHistoryLog")
    public PathLogEntity[] getHistoryLog(@RequestParam("offset") int offset, @RequestParam("limit") int limit){
        int userId = (Integer) session.getAttribute("userId");
        int websiteId = (Integer) session.getAttribute("websiteId");
        PathLogEntity[] historyLogEntities =  DaoUtil.getLogDao().getHistoryLog(userId,websiteId,offset,limit);
        return historyLogEntities;
    }
}
