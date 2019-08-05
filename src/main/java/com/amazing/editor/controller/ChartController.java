package com.amazing.editor.controller;

import com.amazing.editor.entity.BaseEntity;
import com.amazing.editor.entity.MessageEntity;
import com.amazing.editor.entity.chart.GetHistoryMessageEntity;
import com.amazing.editor.entity.socket.ChartSocketEntity;
import com.amazing.editor.utils.DaoUtil;
import com.amazing.editor.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/editor/chart/")
public class ChartController {

    @Autowired
    private HttpSession session;

    /**
     * 发送聊天消息
     * @param messageEntity
     * @return
     */
    @PostMapping("sendMessage")
    public BaseEntity sendMessage(@RequestBody(required = false)MessageEntity messageEntity){
        int userId = (Integer) session.getAttribute("userId");
        int websiteId = (Integer) session.getAttribute("websiteId");
        messageEntity.setUserId(userId);
        messageEntity.setWebsiteId(websiteId);
        DaoUtil.getMessageDao().insert(messageEntity);
        ChartSocketEntity data = new ChartSocketEntity();
        data.setCmd("chartMsg");
        data.setContent(messageEntity.getContent());
        data.setUser(DaoUtil.getUserDao().findById(userId));
        WebSocketServer.sendMessageToWebsiteUsers(websiteId,data);
        BaseEntity result = new BaseEntity();
        return result;
    }

    /**
     * 获取历史消息
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("getHistoryMessage")
    public GetHistoryMessageEntity[] getHistoryMessage(@RequestParam("offset") int offset, @RequestParam("limit") int limit){
        int userId = (Integer) session.getAttribute("userId");
        int websiteId = (Integer) session.getAttribute("websiteId");
        return DaoUtil.getMessageDao().selectHistoryMessage(userId, websiteId, offset, limit);
    }

}
