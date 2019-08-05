package com.amazing.editor.utils;

import com.amazing.editor.entity.LogEntity;
import com.amazing.editor.websocket.WebSocketServer;

import javax.servlet.http.HttpSession;

public class LogUtil {

    public static void write(HttpSession session, int operating, String path, String message){
        int userId = (Integer) session.getAttribute("userId");
        int websiteId = (Integer) session.getAttribute("websiteId");
        LogEntity logEntity = new LogEntity();
        logEntity.setUserId(userId);
        logEntity.setWebsiteId(websiteId);
        logEntity.setOperating(operating);
        logEntity.setPath(path);
        logEntity.setMessage(message);
        DaoUtil.getLogDao().insert(logEntity);
        WebSocketServer.sendMessageToWebsiteUsersByLog(websiteId,DaoUtil.getLogDao().getHistoryLogById(logEntity.getId()));
    }
}
