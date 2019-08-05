package com.amazing.editor.config.intercepors;

import com.amazing.editor.websocket.WebSocketServer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 */
@Component
public class IndexIntercepor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null)
            return true;
        session.setAttribute("websiteId", 1);
        int max = 0;
        for (int userId : WebSocketServer.getUsers().keySet()){
            if (userId > max)
                max = userId;
        }
        session.setAttribute("userId", max % 3 + 1);
        return true;
    }
}
