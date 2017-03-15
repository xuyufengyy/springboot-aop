package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by xuyufengyy on 2017/3/15.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(HttpSession session, Model model){
        List<String> list = (List) session.getAttribute("list");
        model.addAttribute("list", list);
        return "index";
    }
}
