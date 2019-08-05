package com.amazing.editor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/editor/project")
public class ProjectController {

    @RequestMapping(value = "preview", method = RequestMethod.GET)
    public void preview(@RequestParam("path") String path, HttpServletRequest request, HttpServletResponse response){
        int websiteId = (Integer) request.getSession().getAttribute("websiteId");
        try {
            response.sendRedirect("http://preview.amazing-w.top/" + websiteId + "/dev" + path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
