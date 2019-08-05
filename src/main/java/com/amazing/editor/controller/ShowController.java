package com.amazing.editor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/editor")
public class ShowController {

    /**
     * 编辑器页面
     *
     * @return
     */
    @GetMapping("")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("editor/index");
        return mv;
    }
}
