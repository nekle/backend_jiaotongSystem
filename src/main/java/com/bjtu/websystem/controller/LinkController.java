package com.bjtu.websystem.controller;

import com.bjtu.websystem.model.Link;
import com.bjtu.websystem.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class LinkController {

    @Autowired
    LinkService linkService;

    @RequestMapping("/getAllLinks")
    @ResponseBody
    public List<Link> getAllLinks(){
        return linkService.getAllLinks();
    }
}
