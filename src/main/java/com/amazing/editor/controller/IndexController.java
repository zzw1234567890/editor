package com.amazing.editor.controller;

import com.amazing.editor.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/index/")
public class IndexController {
    @GetMapping("login")
    public void login(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null)
            return;
        session.setAttribute("websiteId", 1);
        int max = 0;
        for (int userId : WebSocketServer.getUsers().keySet()){
            if (userId > max)
                max = userId;
        }
        session.setAttribute("userId", max % 3 + 1);
    }
}
