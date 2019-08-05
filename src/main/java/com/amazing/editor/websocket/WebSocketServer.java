package com.amazing.editor.websocket;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amazing.editor.entity.Log.PathLogEntity;
import com.amazing.editor.entity.socket.InitSocketEntity;
import com.amazing.editor.entity.socket.JoinSocketEntity;
import com.amazing.editor.utils.ConfigUtil;
import com.amazing.editor.utils.FilesUtil;
import com.amazing.editor.utils.FinalUtil;
import org.springframework.stereotype.Component;


@Component
@ServerEndpoint(value = "/WebSocket",configurator = HttpSessionConfig.class)
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static Map<Integer, WebSocketServer> users = new HashMap<>();
    // 一个网站对应多个用户的集合
    private static final Map<Integer, Set<Integer>> websites = new HashMap<>();
    // 一个网站对应多个正在编辑的文件的集合
    private static final Map<Integer, Map<Integer, String>> allEditorFiles = new HashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private HttpSession httpSession;

    public static Map<Integer, WebSocketServer> getUsers() {
        return users;
    }

    public static Map<Integer, String> getAllEditorFiles(int websiteId) {
        Map<Integer, String> result = allEditorFiles.get(websiteId);
        if (result == null) {
            allEditorFiles.put(websiteId, new HashMap<>());
            result = allEditorFiles.get(websiteId);
        }
        return result;
    }

    public static void setEditorFiles(int websiteId,Map<Integer, String> value){
        allEditorFiles.put(websiteId, value);
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) throws IOException {
        httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.session = session;
        int userId = (Integer) httpSession.getAttribute("userId");
        int websiteId = (Integer) httpSession.getAttribute("websiteId");
        users.put(userId, this);     //加入set中
        addOnlineCount();           //在线数加1
        Set<Integer> partner = websites.get(websiteId);
        if (partner == null) {
            partner = new HashSet<>();
            websites.put(websiteId, partner);
        }
        partner.add(userId);
        // 单播开始
        InitSocketEntity initData = new InitSocketEntity();
        initData.setUserId(userId);
        initData.setEditorFileEntityList(new ArrayList<>(getAllEditorFiles(websiteId).values()));
        // 获取配置
        String directory = ConfigUtil.getConfigPath(httpSession);
        String jsonText = FilesUtil.readFileContent(directory);
        initData.setConfig(JSON.parse(jsonText));
        sendMessageToUser(userId,initData);
        // 单播结束
        // 广播开始
        JoinSocketEntity data = new JoinSocketEntity();
        data.setUserId(userId);
        sendMessageToWebsiteUsers(websiteId,data);
        // 广播结束
        System.out.println("user " + userId + " connectionOpen, join group " + websiteId + ", websiteUserCount " + websites.get(websiteId).size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        int userId = (Integer) httpSession.getAttribute("userId");
        int websiteId = (Integer) httpSession.getAttribute("websiteId");
        websites.get(websiteId).remove(userId);
        users.remove(userId);  //从set中删除
        getAllEditorFiles(websiteId).remove(userId);
        subOnlineCount();           //在线数减1
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        int userId = (Integer) httpSession.getAttribute(FinalUtil.userId);
        httpSession.setAttribute(FinalUtil.userId,userId);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        System.out.println(error.getMessage());
        session.close();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        int userId = (Integer) httpSession.getAttribute("userId");
        int websiteId = (Integer) httpSession.getAttribute("websiteId");
        try {
            if (this.session.isOpen()) {
                this.session.getBasicRemote().sendText(message);
            } else {
                websites.get(websiteId).remove(userId);
                users.remove(userId);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e){
            users.remove(userId);
            websites.get(websiteId).remove(userId);
        }
    }

    /**
     * 给单个用户发送消息
     *
     * @param userId 用户id
     * @param data   要发送的数据
     */
    public static void sendMessageToUser(int userId, Object data) {
        String msg = JSONObject.toJSONString(data);
        users.get(userId).sendMessage(msg);
    }

    /**
     * 给指定项目的所有成员发送消息
     *
     * @param data      要发送的数据
     */
    public static void sendMessageToWebsiteUsers(int websiteId,Object data) {
        String msg = JSONObject.toJSONString(data);
        try {
            for (int userId : websites.get(websiteId)) {
                users.get(userId).sendMessage(msg);
            }
        } catch (NullPointerException e) {

        }
    }

    public static void sendMessageToWebsiteUsersByLog(int websiteId, PathLogEntity pathLogEntity) {
        String msg = JSONObject.toJSONString(pathLogEntity);
        try {
            for (int userId : websites.get(websiteId)) {
                users.get(userId).sendMessage(msg);
            }
        } catch (NullPointerException e) {

        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}